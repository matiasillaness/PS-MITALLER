package com.mitaller.modulos.cobros.services.impl;


import com.mitaller.modulos.cobros.dominio.Compra;
import com.mitaller.modulos.cobros.dominio.DetalleCompra;
import com.mitaller.modulos.cobros.dominio.ETipoFactura;
import com.mitaller.modulos.cobros.dominio.ETipoPago;
import com.mitaller.modulos.cobros.modelos.*;
import com.mitaller.modulos.cobros.repositorio.CompraRepository;
import com.mitaller.modulos.cobros.repositorio.DetalleCompraRepository;
import com.mitaller.modulos.cobros.services.CobroService;
import com.mitaller.modulos.inventario.repositorio.RepuestoRepository;
import com.mitaller.modulos.proveedores.dominio.Proveedor;
import com.mitaller.modulos.proveedores.modelos.ProveedorResponseDTO;
import com.mitaller.modulos.proveedores.repositorio.ProveedorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
public class CompraServiceImpl implements CobroService {

    private final CompraRepository compraRepository;
    private final DetalleCompraRepository detalleCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final RepuestoRepository repuestoRepository;
    public CompraServiceImpl(CompraRepository compraRepository, DetalleCompraRepository detalleCompraRepository, ProveedorRepository proveedorRepository, RepuestoRepository repuestoRepository) {
        this.compraRepository = compraRepository;
        this.detalleCompraRepository = detalleCompraRepository;
        this.proveedorRepository = proveedorRepository;
        this.repuestoRepository = repuestoRepository;
    }



    @Override
    public ResponseEntity<CompraResponse> registrarCompra(CompraRequest compra) {
        Proveedor proveedor = proveedorRepository.findByIdProveedor(compra.getIdProveedor());
        Compra nuevaCompra = new Compra();
        nuevaCompra.setProveedor(proveedor);
        nuevaCompra.setDescripcion(compra.getDescripcion());
        nuevaCompra.setIva(compra.getIva());
        nuevaCompra.setFecha(nuevaCompra.obtenerFechaActual());
        nuevaCompra.setTipoPago(ETipoPago.fromString(compra.getTipoPago()));
        nuevaCompra.setDadaDeBaja(false);

        List<DetalleCompra> detalles = mapearDetallesAEntity(compra.getDetalleCompraRequest());

        nuevaCompra.addDetalleCompras(detalles);

        nuevaCompra.setTotal(nuevaCompra.calcularTotal());
        actualizarStock(compra.getDetalleCompraRequest());

        compraRepository.save(nuevaCompra);

        nuevaCompra.setNombreComprobante(nuevaCompra.crearNombreComprobante());


        return ResponseEntity.ok(CompraResponse.builder()
                .nombreComprobante(nuevaCompra.getNombreComprobante())
                .fecha(nuevaCompra.getFecha())
                .tipoPago(nuevaCompra.getTipoPago().toString())
                .total(nuevaCompra.getTotal())
                .descripcion(nuevaCompra.getDescripcion())
                .proveedor(
                        ProveedorResponseDTO.builder()
                                .idProveedor(nuevaCompra.getProveedor().getIdProveedor())
                                .nombre(nuevaCompra.getProveedor().getNombre())
                                .direccion(nuevaCompra.getProveedor().getDireccion())
                                .telefono(nuevaCompra.getProveedor().getTelefono())
                                .build()
                )
                .detalles(nuevaCompra.getDetalleCompras().stream().map(this::toDetalleCompraResponse).toList()) //revisar esta parte del codigo
                .build());
    }

    @Override
    public ResponseEntity<CompraResponse> obtenerCompra(String nombreComprobante) {

       Compra compra = compraRepository.findByNombreComprobante(nombreComprobante);
        if (compra == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toCompraResponse(compra));
    }


    //si se anula una compra se debe devolver el stock de los repuestos
    @Override
    @Transactional
    public ResponseEntity<CompraResponse> darDeBajaCompra(String nombreComprobante) {
        Compra compra = compraRepository.findByNombreComprobante(nombreComprobante);
        if (compra == null) {
            return ResponseEntity.notFound().build();
        }

        if (compra.isDadaDeBaja()) {
            return ResponseEntity.badRequest().build();
        }


        compra.getDetalleCompras().forEach(detalleCompra -> {
            detalleCompra.getRepuesto().setStock(detalleCompra.getRepuesto().getStock() - detalleCompra.getCantidad());
            repuestoRepository.save(detalleCompra.getRepuesto());
        });

        compra.setDadaDeBaja(true);
        compraRepository.save(compra);
        return ResponseEntity.ok(toCompraResponse(compra));
    }

    @Override
    public ResponseEntity<List<CompraResponse>> obtenerCompras(FilterCompra filterCompra) {
        List<Compra> compras = compraRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (filterCompra.getFechaInicio() != null) {
            try {
                LocalDateTime fechaInicio = LocalDateTime.parse(filterCompra.getFechaInicio(), formatter);
                compras = compras.stream()
                        .filter(compra -> LocalDateTime.parse(compra.getFecha(), formatter).isAfter(fechaInicio))
                        .collect(Collectors.toList());
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing fechaInicio: " + e.getMessage());
            }
        }

        if (filterCompra.getFechaFin() != null) {
            try {
                LocalDateTime fechaFin = LocalDateTime.parse(filterCompra.getFechaFin(), formatter);
                compras = compras.stream()
                        .filter(compra -> LocalDateTime.parse(compra.getFecha(), formatter).isAfter(fechaFin))
                        .collect(Collectors.toList());
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing fechaFin: " + e.getMessage());
            }
        }

        if (filterCompra.getTipoPago() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.getTipoPago().equals(ETipoPago.fromString(filterCompra.getTipoPago())))
                    .collect(Collectors.toList());
        }

        if (filterCompra.getProveedor() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.getProveedor().getNombre().equals(filterCompra.getProveedor()))
                    .collect(Collectors.toList());
        }

        if (filterCompra.getNombreCompra() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.getNombreComprobante().contains(filterCompra.getNombreCompra()))
                    .collect(Collectors.toList());
        }

        if (filterCompra.getDadaDeBaja() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.isDadaDeBaja() == filterCompra.getDadaDeBaja())
                    .collect(Collectors.toList());
        }

        if (filterCompra.getTotalMayorA() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.getTotal().compareTo(filterCompra.getTotalMayorA()) > 0)
                    .collect(Collectors.toList());
        }

        if (filterCompra.getTotalMenorA() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.getTotal().compareTo(filterCompra.getTotalMenorA()) < 0)
                    .collect(Collectors.toList());
        }

        if (filterCompra.getNumeroDeTelefono() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.getProveedor().getTelefono().contains(filterCompra.getNumeroDeTelefono()))
                    .collect(Collectors.toList());
        }

        if (filterCompra.getEmail() != null) {
            compras = compras.stream()
                    .filter(compra -> compra.getProveedor().getEmail().contains(filterCompra.getEmail()))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(compras.stream().map(this::toCompraResponse).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<String>> getTipoPagos() {
        ETipoPago[] tipoPagos = ETipoPago.values();
        return ResponseEntity.ok(Stream.of(tipoPagos).map(ETipoPago::toString).toList());
    }

    @Override
    public ResponseEntity<List<String>> getTipoFacturas() {
        ETipoFactura[] tipoFacturas = ETipoFactura.values();
        return ResponseEntity.ok(Stream.of(tipoFacturas).map(ETipoFactura::toString).toList());
    }


    //region Metodos privados
    private void actualizarStock(List<DetalleCompraRequest> detalles) {
        if(detalles.isEmpty()) {
            throw new IllegalArgumentException("La lista de detalles no puede estar vacía");
        }
        if (detalles.stream().anyMatch(detalle -> detalle.getCantidad() <= 0)) {
            throw new IllegalArgumentException("La cantidad de repuestos no puede ser menor o igual a 0");
        }
        if (detalles.stream().anyMatch(detalle -> detalle.getIdRepuesto() == null)) {
            throw new IllegalArgumentException("El id del repuesto no puede ser nulo");
        }
        detalles.forEach(detalle -> {
            repuestoRepository.findById(detalle.getIdRepuesto()).ifPresent(repuesto -> {
                repuesto.setStock(repuesto.getStock() + detalle.getCantidad());
                repuestoRepository.save(repuesto);
            });
        });
    }

    private List<DetalleCompra> mapearDetallesAEntity(List<DetalleCompraRequest> detalles) {


        detalles.forEach(detalle -> {
            DetalleCompra nuevoDetalle = new DetalleCompra();
            nuevoDetalle.setCantidad(detalle.getCantidad());
            nuevoDetalle.setRepuesto(repuestoRepository.findById(detalle.getIdRepuesto()).get());
            nuevoDetalle.setPrecioUnitario(detalle.getPrecioUnitario());
            nuevoDetalle.setSubtotal(nuevoDetalle.calcularSubtotal());
        });



        return detalles.stream().map(detalle -> {
            DetalleCompra nuevoDetalle = new DetalleCompra();
            nuevoDetalle.setCantidad(detalle.getCantidad());
            nuevoDetalle.setRepuesto(repuestoRepository.findById(detalle.getIdRepuesto()).get());
            nuevoDetalle.setPrecioUnitario(nuevoDetalle.getRepuesto().getPrecio());
            nuevoDetalle.setSubtotal(nuevoDetalle.calcularSubtotal());
            return nuevoDetalle;
        }).toList();
    }

    private DetalleCompraResponse toDetalleCompraResponse(DetalleCompra detalleCompra) {
        return DetalleCompraResponse.builder()
                .cantidad(detalleCompra.getCantidad())
                .precioUnitario(detalleCompra.getPrecioUnitario())
                .subtotal(detalleCompra.getSubtotal())
                .nombreRepuesto(detalleCompra.getRepuesto().getNombre())
                .build();
    }

    private CompraResponse toCompraResponse(Compra compra) {
        return CompraResponse.builder()
                .nombreComprobante(compra.getNombreComprobante())
                .fecha(compra.getFecha())
                .tipoPago(compra.getTipoPago().toString())
                .total(compra.getTotal())
                .descripcion(compra.getDescripcion())
                .dadaDeBaja(compra.isDadaDeBaja())
                .proveedor(
                        ProveedorResponseDTO.builder()
                                .idProveedor(compra.getProveedor().getIdProveedor())
                                .nombre(compra.getProveedor().getNombre())
                                .direccion(compra.getProveedor().getDireccion())
                                .telefono(compra.getProveedor().getTelefono())
                                .email(compra.getProveedor().getEmail())
                                .descripcion(compra.getProveedor().getDescripcion())
                                .tipoProveedor(compra.getProveedor().getTipoProveedor().toString())
                                .tipoProveedor(compra.getProveedor().getTipoProveedor().toString())
                                .build()
                )
                .detalles(
                        toDetalleCompraResponse(compra.getDetalleCompras())
                )
                .build();
    }

    private List<DetalleCompraResponse> toDetalleCompraResponse(List<DetalleCompra> detalleCompras) {
        return detalleCompras.stream().map(this::toDetalleCompraResponse).toList();
    }

    private String formatFecha(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime.parse(fecha, formatter);
            return fecha;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El formato de fecha no es válido");
        }
    }



    //endregion
}
