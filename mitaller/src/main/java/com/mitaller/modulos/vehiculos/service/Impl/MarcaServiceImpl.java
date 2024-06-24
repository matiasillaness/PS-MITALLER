package com.mitaller.modulos.vehiculos.service.Impl;

import com.mitaller.modulos.comun.exception.ApiRequestException;
import com.mitaller.modulos.comun.servicios.LetterCleanService;
import com.mitaller.modulos.vehiculos.dominio.Marca;
import com.mitaller.modulos.vehiculos.modelos.MarcaResponse;
import com.mitaller.modulos.vehiculos.repositorio.MarcaRepository;
import com.mitaller.modulos.vehiculos.service.MarcaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    private final ModelMapper modelMapper;

    public MarcaServiceImpl(ModelMapper modelMapper, MarcaRepository marcaRepository) {
        this.modelMapper = modelMapper;
        this.marcaRepository = marcaRepository;
    }


    

    @Override
    public ResponseEntity<List<MarcaResponse>> obtenerMarcas(String nombre, Long id, Boolean activo) throws IllegalAccessException {
        List<Marca> marcas = marcaRepository.findAll();

        if (nombre != null) {
            marcas = marcas.stream().filter(marca -> marca.getNombre().contains(nombre.toUpperCase())).collect(Collectors.toList());
        }
        if (id != null) {
            marcas = marcas.stream().filter(marca -> marca.getIdMarca().equals(id)).collect(Collectors.toList());
        }
        if (activo != null) {
            marcas = marcas.stream().filter(marca -> marca.isActivo() == activo).collect(Collectors.toList());
        }
        marcas.sort(Comparator.comparing(Marca::getNombre));

        return ResponseEntity.ok(marcas.stream().map(marca -> modelMapper.map(marca, MarcaResponse.class)).collect(Collectors.toList()));
    }



    @Override
    public ResponseEntity<MarcaResponse> obtenerMarcaPorId(Long id){
        if (id == null) {
            throw new ApiRequestException("Id no puede ser nulo");
        }

        Optional<Marca> marca = marcaRepository.findById(id);

        if (marca.isEmpty()) {
            throw new ApiRequestException("Marca no encontrada");
        }

        return marca.map(value -> ResponseEntity.ok(modelMapper.map(value, MarcaResponse.class))).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @Override
    public ResponseEntity<Boolean> crearMarca(String nombre) {
        try {
            if (marcaRepository.findByNombre(nombre.toUpperCase()) != null) {
                throw new ApiRequestException("Marca no encontrada");
            }
            Marca marca = new Marca();
            marca.setNombre(nombre.toUpperCase());
            marca.setActivo(true);

            marcaRepository.save(marca);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @Override
    public ResponseEntity<Boolean> actualizarMarca(Long id, String nuevoNombre) {
        try {
            if (!marcaRepository.existsById(id)) {
                throw new ApiRequestException("Marca no encontrada");
            }

            Marca marca = marcaRepository.findById(id).orElse(null);
            assert marca != null;
            marca.setNombre(nuevoNombre.toUpperCase());
            marcaRepository.save(marca);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @Override
    public ResponseEntity<Boolean> eliminarMarca(Long id) {
        try {
            if (!marcaRepository.existsById(id)) {
                throw new ApiRequestException("Marca no encontrada");
            }
            marcaRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @Override
    public ResponseEntity<Boolean> activarODesactivarMarca(Long id, boolean activo) {
        try {
            if (!marcaRepository.existsById(id)) {
                return ResponseEntity.status(404).body(false);
            }
            Marca marca = marcaRepository.findById(id).orElse(null);

            if (marca == null) {
                throw new ApiRequestException("Marca no encontrada");
            }

            marca.setActivo(activo);
            marcaRepository.save(marca);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }

    }


}
