package com.mitaller.modulos.inventario.servicios.impl;

import com.mitaller.modulos.comun.servicios.LetterCleanService;
import com.mitaller.modulos.inventario.dominio.MarcaRepuesto;
import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.inventario.dominio.Servicio;
import com.mitaller.modulos.inventario.modelos.FiltersRepuesto;
import com.mitaller.modulos.inventario.modelos.RepuestoRequest;
import com.mitaller.modulos.inventario.modelos.RepuestoResponse;
import com.mitaller.modulos.inventario.repositorio.MarcaRepuestoRepository;
import com.mitaller.modulos.inventario.repositorio.RepuestoRepository;
import com.mitaller.modulos.inventario.servicios.RepuestoService;
import com.mitaller.modulos.vehiculos.modelos.VehiculoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RepuestoServiceImpl implements RepuestoService {
    private final RepuestoRepository repuestoRepository;
    private final ModelMapper modelMapper;

    private final MarcaRepuestoRepository marcaRepository;
    public RepuestoServiceImpl(RepuestoRepository repuestoRepository,
                               ModelMapper modelMapper,
                               MarcaRepuestoRepository marcaRepository) {
        this.repuestoRepository = repuestoRepository;
        this.modelMapper = modelMapper;
        this.marcaRepository = marcaRepository;
    }

    @Override
    public ResponseEntity<RepuestoResponse> obtenerRepuestoPorId(Long idRepuesto)  {

        RepuestoResponse repuestoResponse = repuestoRepository.findById(idRepuesto)
                .map(repuesto -> modelMapper.map(repuesto, RepuestoResponse.class))
                .orElse(null);

        assert repuestoResponse != null;
        repuestoResponse.setMarca(repuestoRepository.findById(idRepuesto).get().getMarca().getNombre());

        return ResponseEntity.ok(repuestoResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<RepuestoResponse> crearRepuesto(RepuestoRequest repuestoRequest) {
        try {
            if (repuestoRequest == null) {
                return ResponseEntity.badRequest().build();
            }

            repuestoRequest.setAllToUpperCase();

            MarcaRepuesto marca = marcaRepository.findById(repuestoRequest.getIdMarca()).orElse(null);
            if (marca == null) {
                throw new IllegalArgumentException("Marca no encontrada");
            }

            Repuesto repuesto = new Repuesto();

            repuesto.setActivo(true);
            repuesto.setMarca(marca);
            repuesto.setNombre(repuestoRequest.getNombre());
            repuesto.setPrecio(repuestoRequest.getPrecio());
            repuesto.setStock(repuestoRequest.getStock());
            repuesto.setDescripcion(repuestoRequest.getDescripcion());

            repuesto = repuestoRepository.save(repuesto);

            RepuestoResponse repuestoResponse = modelMapper.map(repuesto, RepuestoResponse.class);
            repuestoResponse.setMarca(marca.getNombre());

            return ResponseEntity.ok(repuestoResponse);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear el repuesto");
        }
    }

    @Override
    public ResponseEntity<RepuestoResponse> actualizarRepuesto(Long idRepuesto,
                                                               RepuestoRequest repuestoRequest) {

        if (idRepuesto == null || idRepuesto <= 0 || repuestoRequest == null) {
            throw new IllegalArgumentException("Id no puede ser nulo");
        }

        repuestoRequest.setAllToUpperCase();

        Repuesto repuesto = repuestoRepository.findById(idRepuesto).orElse(null);

        if (repuesto == null) {
            throw new IllegalArgumentException("Repuesto no encontrado");
        }
        if (repuestoRequest.getIdMarca() != null) {
            MarcaRepuesto marca = marcaRepository.findById(repuestoRequest.getIdMarca()).orElse(null);
            if (marca == null) {
                throw new IllegalArgumentException("Marca no encontrada");
            }
            repuesto.setMarca(marca);
        }

        if (repuestoRequest.getNombre() != null) {
            repuesto.setNombre(repuestoRequest.getNombre());
        }
        if (repuestoRequest.getDescripcion() != null) {
            repuesto.setDescripcion(repuestoRequest.getDescripcion());
        }
        if (repuestoRequest.getPrecio() != null) {
            repuesto.setPrecio(repuestoRequest.getPrecio());
        }

         repuesto.setStock(repuestoRequest.getStock());



        repuesto = repuestoRepository.save(repuesto);

        RepuestoResponse repuestoResponse = modelMapper.map(repuesto, RepuestoResponse.class);

        repuestoResponse.setMarca(repuestoRepository.findById(idRepuesto).get().getMarca().getNombre());

        return ResponseEntity.ok(repuestoResponse);
    }

    @Override
    public ResponseEntity<RepuestoResponse> eliminarRepuesto(Long idRepuesto) {
        ResponseEntity<RepuestoResponse> responseEntity = validarIdRepuesto(idRepuesto);
        if (responseEntity != null) {
            return responseEntity;
        }
        repuestoRepository.deleteById(idRepuesto);
        throw new IllegalArgumentException("Error al eliminar el repuesto");
    }





    @Override
    public ResponseEntity<List<RepuestoResponse>> obtenerRepuestoPorPrecioMayorOMenorQue(
            BigDecimal precio,
            String tipo){


        if (precio == null || precio.signum() <= 0 || tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("Precio no puede ser nulo o negativo");
        }
        List<Repuesto> repuestos = tipo.equals("mayor")
                ? repuestoRepository.findByPrecioGreaterThan(precio)
                : repuestoRepository.findByPrecioLessThan(precio);

        List<RepuestoResponse> repuestoResponses = repuestos.stream()
                .map(repuesto -> modelMapper.map(repuesto, RepuestoResponse.class))
                .toList();

        repuestoResponses.forEach(repuestoResponse -> repuestoResponse.setMarca(repuestoRepository.findById(repuestoResponse.getIdRepuesto()).get().getMarca().getNombre()));

        return ResponseEntity.ok(repuestoResponses);
    }

    @Override
    public ResponseEntity<List<RepuestoResponse>> obtenerRepuestos(FiltersRepuesto filters) {

        if (repuestoRepository.count() == 0) {
            return ResponseEntity.notFound().build();
        }

        filters.convertirAMayusculas();

        List<Repuesto> repuestos = repuestoRepository.findAll();


        if (filters.getNombre() != null) {
            repuestos = repuestos.stream()
                    .filter(repuesto -> repuesto.getNombre().contains(filters.getNombre()))
                    .collect(Collectors.toList());
        }
        if (filters.getMarca() != null) {
            repuestos = repuestos.stream()
                    .filter(repuesto -> repuesto.getMarca().getNombre().contains(filters.getMarca()))
                    .collect(Collectors.toList());
        }
        if (filters.getPrecio() != null) {
            repuestos = repuestos.stream()
                    .filter(repuesto -> repuesto.getPrecio().compareTo(filters.getPrecio()) == 0)
                    .collect(Collectors.toList());
        }

        Boolean activoBoolean = filters.getActivo();

        if (activoBoolean != null) {
            final boolean activo = activoBoolean;
            repuestos = repuestos.stream()
                    .filter(repuesto -> repuesto.isActivo() == activo)
                    .collect(Collectors.toList());
        }


        //todo: paginacion

        List<Repuesto> sourceList = repuestos;
        sourceList.sort(Comparator.comparing(Repuesto::getNombre).reversed());



        if(filters.getSize() <= 0 || filters.getPage() <= 0) {
            throw new IllegalArgumentException("invalid page size: " + filters.getPage());
        }

        int fromIndex = (filters.getPage() - 1) * filters.getSize();
        if(sourceList.size() <= fromIndex){
            return ResponseEntity.ok(Collections.emptyList());
        }

        sourceList = sourceList.subList(fromIndex, Math.min(fromIndex + filters.getSize(), sourceList.size()));

        //ordenar por nombre


        List<RepuestoResponse> repuestoResponses = sourceList.stream()
                .map(repuesto -> modelMapper.map(repuesto, RepuestoResponse.class))
                .toList();

        repuestoResponses.forEach(repuestoResponse -> repuestoResponse.setMarca(repuestoRepository.findById(repuestoResponse.getIdRepuesto()).get().getMarca().getNombre()));

        return ResponseEntity.ok(repuestoResponses);

    }

    @Override
    public ResponseEntity<RepuestoResponse> activarOInactivarRepuesto(Long idRepuesto, Boolean activo) {
        Optional<Repuesto> responseEntity = repuestoRepository.findById(idRepuesto);

        if (responseEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        Repuesto repuesto = repuestoRepository.findById(idRepuesto).orElse(null);
        if (repuesto == null) {
            throw new IllegalArgumentException("Repuesto no encontrado");
        }

        repuesto.setActivo(activo);
        repuesto = repuestoRepository.save(repuesto);

        RepuestoResponse repuestoResponse = modelMapper.map(repuesto, RepuestoResponse.class);
        repuestoResponse.setMarca(repuestoRepository.findById(idRepuesto).get().getMarca().getNombre());

        return ResponseEntity.ok(repuestoResponse);
    }


    private ResponseEntity<RepuestoResponse> validarIdRepuesto(Long idRepuesto) {
        if (idRepuesto == null || idRepuesto <= 0) {
            return ResponseEntity.badRequest().build();
        }
        throw new IllegalArgumentException("Id no puede ser nulo");
    }
}
