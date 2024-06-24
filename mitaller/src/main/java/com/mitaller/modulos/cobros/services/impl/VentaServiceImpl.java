package com.mitaller.modulos.cobros.services.impl;

import com.mitaller.modulos.cobros.dominio.DetalleVenta;
import com.mitaller.modulos.cobros.dominio.ETipoFactura;
import com.mitaller.modulos.cobros.dominio.Venta;
import com.mitaller.modulos.cobros.dto.InfoServicioUtilizado;
import com.mitaller.modulos.cobros.modelos.DetalleVentaResponse;
import com.mitaller.modulos.cobros.modelos.FilterVenta;
import com.mitaller.modulos.cobros.modelos.VentaRequest;
import com.mitaller.modulos.cobros.modelos.VentaResponse;
import com.mitaller.modulos.cobros.repositorio.DetalleVentaRepository;
import com.mitaller.modulos.cobros.repositorio.VentaRepository;
import com.mitaller.modulos.cobros.services.VentaService;
import com.mitaller.modulos.comun.exception.ApiException;
import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.inventario.dominio.Servicio;
import com.mitaller.modulos.inventario.repositorio.RepuestoRepository;
import com.mitaller.modulos.inventario.repositorio.ServicioRepository;
import com.mitaller.modulos.usuarios.repositorio.UsuarioRepository;
import com.mitaller.modulos.vehiculos.modelos.VehiculoResponse;
import com.mitaller.modulos.vehiculos.repositorio.VehiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
public class VentaServiceImpl implements VentaService {

    private final RepuestoRepository repuestRepository;
    private final VentaRepository ventaRepository;
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;

    private final DetalleVentaRepository detalleVentaRepository;


    public VentaServiceImpl(RepuestoRepository repuestRepository, VentaRepository ventaRepository, ServicioRepository servicioRepository, UsuarioRepository usuarioRepository, VehiculoRepository vehiculoRepository, DetalleVentaRepository detalleVentaRepository) {
        this.repuestRepository = repuestRepository;
        this.ventaRepository = ventaRepository;
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }


    @Override @Transactional()
    public ResponseEntity<VentaResponse> registrarVenta(VentaRequest venta) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String formattedDate = now.format(formatter);





        venta.setDetalleVentaRequest(venta.getDetalleVentaRequest());

           Venta ventaDb = new Venta();
           ventaDb.crearNumeroFactura();
           ventaDb.setTipoPago(venta.getTipoPago());
           ventaDb.setTipoFactura(venta.getTipoFactura());
           ventaDb.setRazonSocial(venta.getRazonSocial());
           ventaDb.setDniCliente(venta.getDniCliente());
           ventaDb.setDireccionCliente(venta.getDireccionCliente());
           ventaDb.setTelefonoCliente(venta.getTelefonoCliente());
           ventaDb.setFecha(formattedDate);
           ventaDb.setDescuento(venta.getDescuento());
           ventaDb.setDescripcion(venta.getDescripcion());
           ventaDb.setUsuario(usuarioRepository.findByEmailIgnoreCase(venta.getEmail()));
           ventaDb.setDadaDeBaja(false);
           ventaDb.setDescripcion(venta.getDescripcion());
           ventaDb.setVehiculo(vehiculoRepository.findById(venta.getIdVehiculo()).get());

           ventaDb.setMercadoPago(venta.isMercadoPago());

        venta.getDetalleVentaRequest().forEach(detalleVentaRequest -> {
            DetalleVenta detalleVenta = new DetalleVenta();

            // Manejar el repuesto si el ID no es nulo
            if (detalleVentaRequest.getIdRepuesto() != null) {
                Optional<Repuesto> repuestoDetalle = repuestRepository.findById(detalleVentaRequest.getIdRepuesto());
                if (repuestoDetalle.isPresent()) {
                    Repuesto repuesto = repuestoDetalle.get();
                    if (repuesto.getStock() < detalleVentaRequest.getCantidad()) {
                        throw new IllegalArgumentException("Error al registrar la venta, stock insuficiente");
                    }

                    detalleVenta.setRepuesto(repuesto);
                    detalleVenta.setPrecioUnitario(repuesto.getPrecio());
                    detalleVenta.setCantidad(detalleVentaRequest.getCantidad());
                    detalleVenta.setSubtotal(detalleVenta.calcularSubtotal());
                    ventaDb.AddDetalleVenta(detalleVenta);

                    repuesto.setStock(repuesto.getStock() - detalleVentaRequest.getCantidad());
                    repuestRepository.save(repuesto);
                } else {
                    throw new IllegalArgumentException("Repuesto no encontrado");
                }
            }

            // Manejar el servicio si el ID no es nulo
            if (detalleVentaRequest.getIdServicio() != null) {
                Optional<Servicio> servicio = servicioRepository.findById(detalleVentaRequest.getIdServicio());
                if (servicio.isPresent()) {
                    detalleVenta.setServicio(servicio.get());
                    detalleVenta.setPrecioUnitario(servicio.get().getPrecio());
                    detalleVenta.setCantidad(detalleVentaRequest.getCantidad());
                    detalleVenta.setSubtotal(detalleVenta.calcularSubtotalServicio());
                    ventaDb.AddDetalleVenta(detalleVenta);
                } else {
                    throw new IllegalArgumentException("Servicio no encontrado");
                }
            }

            // Calcular total de la venta
            BigDecimal total = ventaDb.getDetalleVentas().stream()
                    .map(DetalleVenta::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            ventaDb.setTotal(total);
        });


        ventaDb.setTotal(ventaDb.getTotal().subtract(ventaDb.getDescuento()));
              ventaRepository.save(ventaDb);
              ventaDb.crearNumeroFactura();
              ventaDb.setNumeroFactura(ventaDb.getNumeroFactura());
              ventaRepository.save(ventaDb);


        return convertVentaEnityToVentaResponse(ventaDb);

    }

    @Override //tener en cuenta que si se anula una venta se debe devolver el stock de los
    // repuestos, y si se anula una venta con servicios,
    // se debe devolver el stock de los repuestos que se hayan usado en los servicios
    public ResponseEntity<Boolean> anularVenta(String nombreFactura) throws ApiException {
        Venta ventaDb = ventaRepository.findByNumeroFactura(nombreFactura);

        try {
            ventaDb.getDetalleVentas().forEach(detalleVenta -> {
                if (detalleVenta.getRepuesto() != null) {
                    Repuesto repuesto = detalleVenta.getRepuesto();
                    repuesto.setStock(repuesto.getStock() + detalleVenta.getCantidad());
                    repuestRepository.save(repuesto);
                }
            });
            ventaDb.setDadaDeBaja(true);
            ventaRepository.save(ventaDb);
        } catch (Exception e) {
            throw new ApiException("Error al anular la venta", e, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<List<String>> tiposPago() {
        ETipoFactura[] tipoFacturas = ETipoFactura.values();
        return ResponseEntity.ok(Stream.of(tipoFacturas).map(ETipoFactura::toString).toList());
    }

    @Override
    public ResponseEntity<List<String>> tiposFactura() {
        ETipoFactura[] tipoFacturas = ETipoFactura.values();
        return ResponseEntity.ok(Stream.of(tipoFacturas).map(ETipoFactura::toString).toList());
    }

    @Override
    public ResponseEntity<List<VentaResponse>> obtenerVentas(FilterVenta filterVenta) {
        List<Venta> ventas = ventaRepository.findAll();
        filterVenta.trimAll();

        if (filterVenta.getFechaInicio() != null) {
            ventas = ventas.stream()
                    .filter(venta -> venta.getFecha().compareTo(filterVenta.getFechaInicio()) >= 0)
                    .toList();
        }

        if (filterVenta.getFechaFin() != null) {
            ventas = ventas.stream()
                    .filter(venta -> venta.getFecha().compareTo(filterVenta.getFechaFin()) <= 0)
                    .toList();
        }

        if (filterVenta.getNumeroFactura() != null) {
            ventas = ventas.stream()
                    .filter(venta -> venta.getNumeroFactura().contains(filterVenta.getNumeroFactura()))
                    .toList();
        }

        if (filterVenta.getDescripcion() != null) {
            ventas = ventas.stream()
                    .filter(venta -> venta.getDescripcion().contains(filterVenta.getDescripcion()))
                    .toList();
        }

        if (filterVenta.getCliente() != null) {
            ventas = ventas.stream()
                    .filter(venta -> venta.getDniCliente().contains(filterVenta.getCliente()))
                    .toList();
        }

        if (filterVenta.getDadaDeBaja() != null) {
            ventas = ventas.stream()
                    .filter(venta -> venta.isDadaDeBaja() == filterVenta.getDadaDeBaja())
                    .toList();
        }

        if (filterVenta.getTipoFactura() != null) {
            ventas = ventas.stream()
                    .filter(venta ->  venta.getTipoFactura().equals(filterVenta.getTipoFactura()))
                    .toList();
        }

        if (filterVenta.getTipoPago() != null) {
            ventas = ventas.stream()
                    .filter(venta -> venta.getTipoPago().equals(filterVenta.getTipoPago()))
                    .toList();
        }

        return ResponseEntity.ok(convertVentaEnityToVentaResponse(ventas));
    }


    //todo: test this method
    @Override
    public ResponseEntity<VentaResponse> obtenerVenta(String nombreComprobante) throws ApiException {
        Venta ventaDb = ventaRepository.findByNumeroFactura(nombreComprobante);

        if (ventaDb == null) {
            throw new ApiException("Venta no encontrada",null, HttpStatus.NOT_FOUND, null);
        }

        return convertVentaEnityToVentaResponse(ventaDb);
    }

    private ResponseEntity<VentaResponse> convertVentaEnityToVentaResponse(Venta ventaDb) {
        VentaResponse ventaResponse = VentaResponse.builder()
                .descripcion(ventaDb.getDescripcion())
                .dniCliente(ventaDb.getDniCliente())
                .numeroFactura(ventaDb.getNumeroFactura())
                .direccionCliente(ventaDb.getDireccionCliente())
                .razonSocial(ventaDb.getRazonSocial())
                .telefonoCliente(ventaDb.getTelefonoCliente())
                .tipoFactura(ventaDb.getTipoFactura().toString())
                .tipoPago(ventaDb.getTipoPago().toString())
                .descuento(ventaDb.getDescuento())
                .total(ventaDb.getTotal())
                .fecha(ventaDb.getFecha())
                .vehiculo(
                        VehiculoResponse.builder()
                                .marca(String.valueOf(ventaDb.getVehiculo().getMarca().getNombre()))
                                .modelo(ventaDb.getVehiculo().getModelo())
                                .patente(ventaDb.getVehiculo().getPatente())
                                .kilometraje(ventaDb.getVehiculo().getKilometraje())
                                .observaciones(ventaDb.getVehiculo().getObservaciones())
                                .color(String.valueOf(ventaDb.getVehiculo().getColor()))
                                .numeroChasis(ventaDb.getVehiculo().getNumeroChasis())
                                .tipoVehiculo(ventaDb.getVehiculo().getTipoVehiculo().toString())
                                .activo(ventaDb.getVehiculo().isActivo())
                                .idVehiculo(ventaDb.getVehiculo().getIdVehiculo())
                                .build()
                )
                .nombreEmpleado(ventaDb.getUsuario().getNombre() + " " + ventaDb.getUsuario().getApellido())
                .estado(ventaDb.isDadaDeBaja())
                .dadaDeBaja(ventaDb.isDadaDeBaja())
                .detalles(ventaDb.getDetalleVentas().stream().map(detalleVenta -> DetalleVentaResponse.builder()
                        .cantidad(detalleVenta.getCantidad())
                        .precioUnitario(detalleVenta.getPrecioUnitario())
                        .subtotal(detalleVenta.getSubtotal())
                        .descripcion(
                                detalleVenta.getRepuesto() != null ?
                                        detalleVenta.getRepuesto().getNombre() :
                                        detalleVenta.getServicio().getNombre()
                        )
                        .nombreRepuesto(detalleVenta.getRepuesto() != null ? detalleVenta.getRepuesto().getNombre() : null)
                        .nombreServicio(detalleVenta.getServicio() != null ? detalleVenta.getServicio().getNombre() : null)
                        .build()).toList()

                ).build();

        return ResponseEntity.ok(ventaResponse);
    }

    private List<VentaResponse> convertVentaEnityToVentaResponse(List<Venta> ventas) {
        return ventas.stream().map(ventaDb -> VentaResponse.builder()
                .descripcion(ventaDb.getDescripcion())
                .dniCliente(ventaDb.getDniCliente())
                .numeroFactura(ventaDb.getNumeroFactura())
                .direccionCliente(ventaDb.getDireccionCliente())
                .descuento(ventaDb.getDescuento())
                .razonSocial(ventaDb.getRazonSocial())
                .telefonoCliente(ventaDb.getTelefonoCliente())
                .tipoFactura(ventaDb.getTipoFactura().toString())
                .tipoPago(ventaDb.getTipoPago().toString())
                .total(ventaDb.getTotal())
                .fecha(ventaDb.getFecha())
                .vehiculo(
                        VehiculoResponse.builder()
                                .marca(String.valueOf(ventaDb.getVehiculo().getMarca().getNombre()))
                                .modelo(ventaDb.getVehiculo().getModelo())
                                .patente(ventaDb.getVehiculo().getPatente())
                                .kilometraje(ventaDb.getVehiculo().getKilometraje())
                                .observaciones(ventaDb.getVehiculo().getObservaciones())
                                .color(String.valueOf(ventaDb.getVehiculo().getColor()))
                                .numeroChasis(ventaDb.getVehiculo().getNumeroChasis())
                                .tipoVehiculo(ventaDb.getVehiculo().getTipoVehiculo().toString())
                                .activo(ventaDb.getVehiculo().isActivo())
                                .idVehiculo(ventaDb.getVehiculo().getIdVehiculo())
                                .build()
                )
                .nombreEmpleado(ventaDb.getUsuario().getNombre() + " " + ventaDb.getUsuario().getApellido())
                .estado(ventaDb.isDadaDeBaja())
                .dadaDeBaja(ventaDb.isDadaDeBaja())
                .detalles(ventaDb.getDetalleVentas().stream().map(detalleVenta -> DetalleVentaResponse.builder()
                        .cantidad(detalleVenta.getCantidad())
                        .precioUnitario(detalleVenta.getPrecioUnitario())
                        .subtotal(detalleVenta.getSubtotal())
                        .descripcion(
                                detalleVenta.getRepuesto() != null ?
                                        detalleVenta.getRepuesto().getNombre() :
                                        detalleVenta.getServicio().getNombre()
                        )
                        .nombreRepuesto(detalleVenta.getRepuesto() != null ? detalleVenta.getRepuesto().getNombre() : null)
                        .nombreServicio(detalleVenta.getServicio() != null ? detalleVenta.getServicio().getNombre() : null)
                        .build()).toList()

                ).build()).toList();
    }


    @Override
    public List<InfoServicioUtilizado> obtenerServiciosMasUtilizados() {
        List<Object[]> resultados = detalleVentaRepository.findServiciosMasUtilizados();

        return resultados.stream()
                .map(result -> {
                    String nombre = (String) result[0];
                    Long cantidad = (Long) result[1];
                    return new InfoServicioUtilizado(nombre, cantidad);
                })
                .collect(Collectors.toList());
    }

}
