package com.mitaller.modulos.vehiculos.service.Impl;

import com.mitaller.modulos.comun.exception.ApiRequestException;
import com.mitaller.modulos.comun.servicios.LetterCleanService;
import com.mitaller.modulos.vehiculos.dominio.EColor;
import com.mitaller.modulos.vehiculos.dominio.ETipoVehiculo;
import com.mitaller.modulos.vehiculos.dominio.Marca;
import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import com.mitaller.modulos.vehiculos.modelos.FiltersRequestService;
import com.mitaller.modulos.vehiculos.modelos.VehiculoRequest;
import com.mitaller.modulos.vehiculos.modelos.VehiculoResponse;
import com.mitaller.modulos.vehiculos.repositorio.MarcaRepository;
import com.mitaller.modulos.vehiculos.repositorio.VehiculoRepository;
import com.mitaller.modulos.vehiculos.service.VehiculoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class VehiculoServiceImpl implements VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final MarcaRepository marcaRepository;
    private final ModelMapper modelMapper;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, ModelMapper modelMapper, MarcaRepository marcaRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.modelMapper = modelMapper;
        this.marcaRepository = marcaRepository;
    }

    @Override
    @Transactional
    public boolean crearVehiculo(VehiculoRequest vehiculoRequest) {
            vehiculoRequest.setAllToUpperCase();

            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setActivo(true);
            modelMapper.map(vehiculoRequest, vehiculo);

            Marca marca = marcaRepository.findById(vehiculoRequest.getMarcaId()).orElse(null);

            if (marca == null) {
                throw new ApiRequestException("Marca no encontrada");
            }

            vehiculo.setMarca(marca);

            vehiculoRepository.save(vehiculo);

            return true;
    }

    @Override
    public boolean actualizarVehiculo(VehiculoRequest vehiculoRequest, Long id) {
        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
            vehiculoRequest.setAllToUpperCase();
            if (vehiculo != null) {

                // Mapear los datos del vehiculoRequest al vehiculo
                vehiculo.setPatente(vehiculoRequest.getPatente());
                vehiculo.setModelo(vehiculoRequest.getModelo());
                vehiculo.setObservaciones(vehiculoRequest.getObservaciones());
                vehiculo.setTipoVehiculo(ETipoVehiculo.valueOf(vehiculoRequest.getTipoVehiculo()));
                vehiculo.setKilometraje(vehiculoRequest.getKilometraje());
                vehiculo.setNumeroChasis(vehiculoRequest.getNumeroChasis());
                vehiculo.setColor(EColor.valueOf(vehiculoRequest.getColor()));

                // Obtener y asignar la marca
                Marca marca = marcaRepository.findById(vehiculoRequest.getMarcaId()).orElse(null);
                vehiculo.setMarca(marca);

                // Guardar el vehiculo actualizado
                vehiculoRepository.save(vehiculo);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApiRequestException("Error al actualizar vehiculo");
        }
    }




    @Override
    public boolean eliminarVehiculo(Long id) {
        try {
            vehiculoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ApiRequestException("Error al eliminar vehiculo");
        }
    }

    @Override
    public List<VehiculoResponse> obtenerVehiculos(FiltersRequestService filters) throws IllegalAccessException {

        filters.convertirAMayusculas();

        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        Stream<Vehiculo> vehiculosFiltradosStream = vehiculos.stream();

        if (filters.getPatente() != null) {
            vehiculosFiltradosStream = vehiculosFiltradosStream.filter(vehiculo -> vehiculo.getPatente().contains(filters.getPatente()));
        }

        if (filters.getModelo() != null) {
            vehiculosFiltradosStream = vehiculosFiltradosStream.filter(vehiculo -> vehiculo.getModelo().contains(filters.getModelo()));
        }

        if (filters.getTipoVehiculo() != null) {
            vehiculosFiltradosStream = vehiculosFiltradosStream.filter(vehiculo -> vehiculo.getTipoVehiculo().toString().toLowerCase().contains(filters.getTipoVehiculo().toLowerCase()));
        }

        if (filters.getActivo() != null) {
            vehiculosFiltradosStream = vehiculosFiltradosStream.filter(vehiculo -> vehiculo.isActivo() == filters.getActivo());
        }




        //todo: PAGINACION COMPLETA

        List<Vehiculo> sourceList = vehiculosFiltradosStream.sorted(Comparator.comparing(Vehiculo::getIdVehiculo).reversed()).collect(Collectors.toList());


        if(filters.getSize() <= 0 || filters.getPage() <= 0) {
            throw new IllegalArgumentException("invalid page size: " + filters.getPage());
        }

        int fromIndex = (filters.getPage() - 1) * filters.getSize();
        if(sourceList.size() <= fromIndex){
            return Collections.emptyList();
        }

        sourceList = sourceList.subList(fromIndex, Math.min(fromIndex + filters.getSize(), sourceList.size()));

        //todo: PAGINACION COMPLETA



        return sourceList.stream()
                .map(vehiculo -> VehiculoResponse.builder()
                        .patente(vehiculo.getPatente())
                        .modelo(vehiculo.getModelo())
                        .observaciones(LetterCleanService.convertirFormato(vehiculo.getObservaciones()))
                        .idVehiculo(vehiculo.getIdVehiculo())
                        .tipoVehiculo(String.valueOf(vehiculo.getTipoVehiculo().toString()))
                        .marca(
                                vehiculo.getMarca() != null ?
                                        vehiculo.getMarca().getNombre() :
                                        null
                        )
                        .kilometraje(vehiculo.getKilometraje())
                        .numeroChasis(vehiculo.getNumeroChasis())
                        .color(String.valueOf(vehiculo.getColor()))
                        .activo(vehiculo.isActivo())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public VehiculoResponse obtenerVehiculoPorId(Long id) throws IllegalAccessException {
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
        if (vehiculo != null) {

            return VehiculoResponse.builder()
                    .patente(vehiculo.getPatente())
                    .modelo(vehiculo.getModelo())
                    .observaciones(LetterCleanService.convertirFormato(vehiculo.getObservaciones()))
                    .idVehiculo(vehiculo.getIdVehiculo())
                    .tipoVehiculo(LetterCleanService.convertirFormato(String.valueOf(vehiculo.getTipoVehiculo())))
                    .kilometraje(vehiculo.getKilometraje())
                    .numeroChasis(vehiculo.getNumeroChasis())
                    .marca(
                            vehiculo.getMarca() != null ?
                                    vehiculo.getMarca().getNombre() :
                                    null
                    )
                    .color(String.valueOf(vehiculo.getColor()))
                    .activo(vehiculo.isActivo())
                    .build();

        } else {
            throw new IllegalAccessException("Vehiculo no encontrado");
        }
    }

    @Override
    public List<String> obtenerColores() {
        EColor[] colores = EColor.values();
        List<String> listaColores = new ArrayList<>();
        for (EColor color : colores) {
           listaColores.add(color.name());
        }

        //ordenamos la lista
        Collections.sort(listaColores);

        return listaColores;
    }

    @Override
    public List<String> obtenerTiposVehiculos() {
        ETipoVehiculo[] tiposVehiculos = ETipoVehiculo.values();
        List<String> listaTiposVehiculos = new ArrayList<>();
        for (ETipoVehiculo tipoVehiculo : tiposVehiculos) {
            listaTiposVehiculos.add(tipoVehiculo.name());
        }

        //ordenamos la lista
        Collections.sort(listaTiposVehiculos);

        return listaTiposVehiculos;
    }

    @Override
    public VehiculoResponse obtenerVehiculoPorPlaca(String patente) throws IllegalAccessException {
        Vehiculo vehiculo = vehiculoRepository.findByPatenteIgnoreCase(patente);
        if (vehiculo != null) {
            return VehiculoResponse.builder()
                    .patente(vehiculo.getPatente())
                    .modelo(vehiculo.getModelo())
                    .observaciones(LetterCleanService.convertirFormato(vehiculo.getObservaciones()))
                    .idVehiculo(vehiculo.getIdVehiculo())
                    .tipoVehiculo(LetterCleanService.convertirFormato(String.valueOf(vehiculo.getTipoVehiculo())))
                    .kilometraje(vehiculo.getKilometraje())
                    .numeroChasis(vehiculo.getNumeroChasis())
                    .marca(
                            vehiculo.getMarca() != null ?
                                    vehiculo.getMarca().getNombre() :
                                    null
                    )
                    .color(String.valueOf(vehiculo.getColor()))
                    .activo(vehiculo.isActivo())
                    .build();
        } else {
            throw new IllegalAccessException("Vehiculo no encontrado");
        }
    }

    @Override
    public boolean activarODesactivarVehiculo(Long id, boolean activo) {
        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
            if (vehiculo != null) {
                vehiculo.setActivo(activo);
                vehiculoRepository.save(vehiculo);
                return true;
            } else {
                throw new ApiRequestException("Vehiculo no encontrado");
            }
        } catch (Exception e) {
            throw new ApiRequestException("Error al activar/desactivar vehiculo");
        }
    }


}
