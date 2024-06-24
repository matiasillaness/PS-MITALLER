package com.mitaller.modulos.proveedores.service.impl;


import com.mitaller.modulos.proveedores.dominio.ETipoProveedor;
import com.mitaller.modulos.proveedores.dominio.Proveedor;
import com.mitaller.modulos.proveedores.modelos.FilterProveedor;
import com.mitaller.modulos.proveedores.modelos.ProveedorRequest;
import com.mitaller.modulos.proveedores.modelos.ProveedorResponseDTO;
import com.mitaller.modulos.proveedores.repositorio.ProveedorRepository;
import com.mitaller.modulos.proveedores.service.ProveedorService;
import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    private final ProveedorRepository proveedorRepository;
    private final ModelMapper modelMapper;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, ModelMapper modelMapper) {
        this.proveedorRepository = proveedorRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<Boolean> crearProveedor(ProveedorRequest proveedorRequest) {
        try{
            Proveedor proveedor = modelMapper.map(proveedorRequest, Proveedor.class);
            proveedorRepository.save(proveedor);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @Override
    public ResponseEntity<Boolean> actualizarProveedor(ProveedorRequest proveedorRequest, Long idProveedor) {
        try {
            Proveedor proveedor = proveedorRepository.findById(idProveedor).orElse(null);
            if (proveedor == null) {
                return ResponseEntity.ok(false);
            }

            proveedor.setNombre(proveedorRequest.getNombre());
            proveedor.setTelefono(proveedorRequest.getTelefono());
            proveedor.setDireccion(proveedorRequest.getDireccion());
            proveedor.setEmail(proveedorRequest.getEmail());
            proveedor.setDescripcion(proveedorRequest.getDescripcion());
            proveedor.setTipoProveedor(ETipoProveedor.valueOf(proveedorRequest.getTipoProveedor()));


            proveedorRepository.save(proveedor);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @Override
    public ResponseEntity<Boolean> eliminarProveedor(Long idProveedor) {
        try {
            proveedorRepository.deleteById(idProveedor);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el proveedor, probablemente este asociado a productos y transacciones");
        }
    }

    /*
    @Override
    public ResponseEntity<Boolean> bajaLogicaProveedor(Long id) {
        try {
            Proveedor proveedor = proveedorRepository.findById(id).orElse(null);
            if (proveedor == null) {
                return ResponseEntity.ok(false);
            }
            proveedor.setActivo(false);
            proveedorRepository.save(proveedor);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }*/

    @Override
    public ResponseEntity<List<ProveedorResponseDTO>> obtenerProveedor(FilterProveedor filterProveedor) {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        Stream<Proveedor> proveedorFiltrados = proveedores.stream();

        if (filterProveedor.getNombre() != null) {
            proveedorFiltrados = proveedorFiltrados.filter(proveedor -> proveedor.getNombre().toLowerCase().contains(filterProveedor.getNombre().toLowerCase()));
        }
        if (filterProveedor.getTelefono() != null) {
            proveedorFiltrados = proveedorFiltrados.filter(proveedor -> proveedor.getTelefono().contains(filterProveedor.getTelefono()));
        }
        if (filterProveedor.getEmail() != null) {
            proveedorFiltrados = proveedorFiltrados.filter(proveedor -> proveedor.getEmail().toLowerCase().contains(filterProveedor.getEmail().toLowerCase()));
        }
        if (filterProveedor.getTipoProveedor() != null) {
            proveedorFiltrados = proveedorFiltrados.filter(proveedor -> proveedor.getTipoProveedor().toString().contains(filterProveedor.getTipoProveedor()));
        }
        List<ProveedorResponseDTO> proveedorResponseDTOS = new ArrayList<>();

        //todo: PAGINACION COMPLETA

        List<Proveedor> sourceList = proveedorFiltrados.sorted(Comparator.comparing(Proveedor::getIdProveedor).reversed()).collect(Collectors.toList());

        if(filterProveedor.getSize() <= 0 || filterProveedor.getPage() <= 0) {
            throw new IllegalArgumentException("invalid page size: " + filterProveedor.getPage());
        }

        int fromIndex = (filterProveedor.getPage() - 1) * filterProveedor.getSize();
        if(sourceList.size() <= fromIndex){
            return ResponseEntity.ok(Collections.emptyList());
        }

        sourceList = sourceList.subList(fromIndex, Math.min(fromIndex + filterProveedor.getSize(), sourceList.size()));

        //todo: PAGINACION COMPLETA




        sourceList.forEach(proveedor -> {
            ProveedorResponseDTO proveedorResponseDTO = ProveedorResponseDTO.builder(
            ).idProveedor(proveedor.getIdProveedor()).nombre(proveedor.getNombre()).telefono(proveedor.getTelefono()).direccion(proveedor.getDireccion()).email(proveedor.getEmail()).descripcion(proveedor.getDescripcion()).tipoProveedor(proveedor.getTipoProveedor().toString()
            ).build();
            proveedorResponseDTOS.add(proveedorResponseDTO);
        });

        return ResponseEntity.ok(proveedorResponseDTOS);
    }

    @Override
    public ResponseEntity<ProveedorResponseDTO> obtenerProveedorPorId(Long idProveedor) {
        Proveedor proveedor = proveedorRepository.findById(idProveedor).orElse(null);
        if (proveedor == null) {
            return ResponseEntity.ok(null);
        }
        ProveedorResponseDTO proveedorResponseDTO = modelMapper.map(proveedor, ProveedorResponseDTO.class);
        return ResponseEntity.ok(proveedorResponseDTO);
    }
}
