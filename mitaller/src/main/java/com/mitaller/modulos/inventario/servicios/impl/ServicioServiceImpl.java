package com.mitaller.modulos.inventario.servicios.impl;

import com.mitaller.modulos.comun.servicios.LetterCleanService;
import com.mitaller.modulos.inventario.dominio.ETipoServicio;
import com.mitaller.modulos.inventario.dominio.Servicio;
import com.mitaller.modulos.inventario.modelos.FilterServicio;
import com.mitaller.modulos.inventario.modelos.ServicioRequest;
import com.mitaller.modulos.inventario.modelos.ServicioResponse;
import com.mitaller.modulos.inventario.repositorio.ServicioRepository;
import com.mitaller.modulos.inventario.servicios.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    private final ModelMapper modelMapper;

    public ServicioServiceImpl(ServicioRepository servicioRepository, ModelMapper modelMapper) {
        this.servicioRepository = servicioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ServicioResponse> obtenerServicio(Long idServicio) throws IllegalAccessException {

        ResponseEntity<ServicioResponse> respuestaValidacion = validarIdServicio(idServicio);
        if (respuestaValidacion != null) {
            return respuestaValidacion;
        }

        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new NoSuchElementException("El servicio con ID " + idServicio + " no se encuentra"));

        ServicioResponse servicioResponse = modelMapper.map(servicio, ServicioResponse.class);

        LetterCleanService.convertirStringToNormal(servicioResponse);

        return ResponseEntity.ok(servicioResponse);

    }

    @Override
    @Transactional
    public ResponseEntity<ServicioResponse> crearServicio(ServicioRequest servicioRequest) throws IllegalAccessException {
        if (servicioRequest == null) {
            return ResponseEntity.badRequest().build();
        }

        servicioRequest.setAllUpperCase();

        Servicio servicio = modelMapper.map(servicioRequest, Servicio.class);
        servicio = servicioRepository.save(servicio);
        ServicioResponse servicioResponse = modelMapper.map(servicio, ServicioResponse.class);

        LetterCleanService.convertirStringToNormal(servicioResponse);

        return ResponseEntity.ok(servicioResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ServicioResponse> actualizarServicio(Long idServicio, ServicioRequest servicioRequest) throws IllegalAccessException {
        if (idServicio == null || idServicio <= 0 || servicioRequest == null) {
            return ResponseEntity.badRequest().build();
        }

        servicioRequest.setAllUpperCase();

        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new NoSuchElementException("El servicio con ID " + idServicio + " no se encuentra"));
        modelMapper.map(servicioRequest, servicio);



        servicio = servicioRepository.save(servicio);
        ServicioResponse servicioResponse = modelMapper.map(servicio, ServicioResponse.class);

        LetterCleanService.convertirStringToNormal(servicioResponse);

        return ResponseEntity.ok(servicioResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ServicioResponse> eliminarServicio(Long idServicio) {
       if (idServicio == null || idServicio <= 0) {
            return ResponseEntity.badRequest().build();
        }
        servicioRepository.deleteById(idServicio);
        return ResponseEntity.ok().build();
    }







    @Override
    public ResponseEntity<List<ServicioResponse>> obtenerServicioPorPrecioMayorOMenorQue(BigDecimal precio, String tipo) throws IllegalAccessException {
        if (precio == null || precio.signum() >= 0 || tipo == null || tipo.isEmpty() || (!tipo.equals("mayor") && !tipo.equals("menor"))){
            return ResponseEntity.badRequest().build();
        }
        List<Servicio> servicios = tipo.equals("mayor") ?
                servicioRepository.findByPrecioGreaterThan(precio) :
                servicioRepository.findByPrecioLessThan(precio);
        List<ServicioResponse> servicioResponses = servicios.stream()
                .map(servicio -> modelMapper.map(servicio, ServicioResponse.class))
                .toList();

        LetterCleanService.convertirStringToNormal(servicioResponses);

        return ResponseEntity.ok(servicioResponses);
    }

    @Override
    public ResponseEntity<List<ServicioResponse>> obtenerServicios(FilterServicio filters) throws IllegalAccessException {
        if (filters == null) {
            return ResponseEntity.badRequest().build();
        }

        filters.setAllUpperCase();

        List<Servicio> servicios = servicioRepository.findAll();

        List<Servicio> serviciosFiltrados = servicios;

        if (filters.getNombre() != null) {
            serviciosFiltrados = serviciosFiltrados.stream()
                    .filter(servicio -> servicio.getNombre().contains(filters.getNombre()))
                    .toList();
        }
        if (filters.getTipo() != null) {
            serviciosFiltrados = serviciosFiltrados.stream()
                    .filter(servicio -> servicio.getTipo().toString().toLowerCase().contains(filters.getTipo().toLowerCase()))
                    .toList();
        }
        if (filters.getPrecio() != null) {
            serviciosFiltrados = serviciosFiltrados.stream()
                    .filter(servicio -> servicio.getPrecio().compareTo(filters.getPrecio()) == 0)
                    .toList();
        }
        if (filters.getActivo() != null) {
            serviciosFiltrados = serviciosFiltrados.stream()
                    .filter(servicio -> servicio.isActivo() == filters.getActivo())
                    .toList();
        }


        //TODO: PAGINACION

        List<Servicio> sourceList = serviciosFiltrados;


        if(filters.getSize() <= 0 || filters.getPage() <= 0) {
            throw new IllegalArgumentException("invalid page size: " + filters.getPage());
        }

        int fromIndex = (filters.getPage() - 1) * filters.getSize();
        if(sourceList.size() <= fromIndex){
            return ResponseEntity.ok(Collections.emptyList());
        }

        sourceList = sourceList.subList(fromIndex, Math.min(fromIndex + filters.getSize(), sourceList.size()));


        //todo: PAGINACION COMPLETA

        List<ServicioResponse> servicioResponses = sourceList.stream()
                .map(servicio -> modelMapper.map(servicio, ServicioResponse.class))
                .toList();

        LetterCleanService.convertirStringToNormal(servicioResponses);

        return ResponseEntity.ok(servicioResponses);
    }

    @Override
    public ResponseEntity<ServicioResponse> activarOInactivarServicio(Long idServicio, Boolean activo) throws IllegalAccessException {
        ResponseEntity<ServicioResponse> respuestaValidacion = validarIdServicio(idServicio);
        if (respuestaValidacion != null) {
            return respuestaValidacion;
        }

        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new NoSuchElementException("El servicio con ID " + idServicio + " no se encuentra"));

        servicio.setActivo(activo);
        servicio = servicioRepository.save(servicio);
        ServicioResponse servicioResponse = modelMapper.map(servicio, ServicioResponse.class);

        LetterCleanService.convertirStringToNormal(servicioResponse);

        return ResponseEntity.ok(servicioResponse);
    }


    private ResponseEntity<List<String>> obtenerTiposDeServicios() throws IllegalAccessException {
        ETipoServicio[] tiposServicios = ETipoServicio.values();
        List<String> tipos = Arrays.stream(tiposServicios)
                .map(ETipoServicio::toString)
                .toList();

        LetterCleanService.convertirStringToNormal(tipos);
        LetterCleanService.limpiarGuionesBajos(tipos);

        return ResponseEntity.ok(tipos);
    }

    private ResponseEntity<ServicioResponse> validarIdServicio(Long idServicio) {
        if (idServicio == null || idServicio <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return null;
    }
}
