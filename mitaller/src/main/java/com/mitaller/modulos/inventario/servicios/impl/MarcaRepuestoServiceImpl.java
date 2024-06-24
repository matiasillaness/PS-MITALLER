package com.mitaller.modulos.inventario.servicios.impl;

import com.mitaller.modulos.inventario.dominio.MarcaRepuesto;
import com.mitaller.modulos.inventario.repositorio.MarcaRepuestoRepository;
import com.mitaller.modulos.inventario.servicios.MarcaRepuestoService;
import com.mitaller.modulos.vehiculos.dominio.Marca;
import com.mitaller.modulos.vehiculos.modelos.MarcaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MarcaRepuestoServiceImpl implements MarcaRepuestoService {

    private final MarcaRepuestoRepository marcaRepository;

    private final ModelMapper modelMapper;

    public MarcaRepuestoServiceImpl(ModelMapper modelMapper, MarcaRepuestoRepository marcaRepository) {
        this.modelMapper = modelMapper;
        this.marcaRepository = marcaRepository;
    }

    @Override
    public ResponseEntity<List<MarcaResponse>> obtenerMarcas(String nombre, Long id, Boolean activo){
        List<MarcaRepuesto> marcas = marcaRepository.findAll();

        if (nombre != null) {
            marcas.stream().filter(marca -> marca.getNombre().contains(nombre.toUpperCase())).collect(Collectors.toList());
        }
        if (id != null) {
            marcas.stream().filter(marca -> marca.getIdMarca().equals(id)).collect(Collectors.toList());
        }
        if (activo != null) {
            marcas = marcas.stream().filter(marca -> marca.isActivo() == activo).collect(Collectors.toList());
        }



        //ordenar por id desc
        marcas.sort(Comparator.comparing(MarcaRepuesto::getNombre));



        return ResponseEntity.ok(marcas.stream().map(marca -> modelMapper.map(marca, MarcaResponse.class)).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<MarcaResponse> obtenerMarcaPorId(Long id){

        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser nulo");
        }
        if (marcaRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Marca no encontrada");
        }

        Optional<MarcaRepuesto> marca = marcaRepository.findById(id);
        return marca.map(value -> ResponseEntity.ok(modelMapper.map(value, MarcaResponse.class))).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @Override
    public ResponseEntity<Boolean> crearMarca(String nombre) {
        try {
            if (marcaRepository.findByNombre(nombre.toUpperCase()) != null) {
                throw new IllegalAccessException("Marca ya existe");
            }
            if (nombre.isEmpty()) {
                throw new IllegalAccessException("Nombre no puede ser vacío");
            }

            MarcaRepuesto marca = new MarcaRepuesto();
            marca.setNombre(nombre.toUpperCase());
            marca.setActivo(true);

            marcaRepository.save(marca);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear la marca");
        }
    }

    @Override
    public ResponseEntity<Boolean> actualizarMarca(Long id, String nuevoNombre) {
        try {
            if (!marcaRepository.existsById(id)) {
                throw new IllegalAccessException("Marca no encontrada");
            }
            if (nuevoNombre.isEmpty()) {
                throw new IllegalAccessException("Nombre no puede ser vacío");
            }

            MarcaRepuesto marca = marcaRepository.findById(id).orElse(null);
            assert marca != null;
            marca.setNombre(nuevoNombre.toUpperCase());
            marcaRepository.save(marca);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar la marca");
        }
    }

    @Override
    public ResponseEntity<Boolean> eliminarMarca(Long id) {
        try {
            if (!marcaRepository.existsById(id)) {
                return ResponseEntity.status(404).body(false);
            }
            marcaRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al eliminar la Marca");
        }
    }

    @Override
    public ResponseEntity<Boolean> activarODesactivarMarca(Long id, boolean activo) {
        try {
            if (!marcaRepository.existsById(id)) {
                throw new IllegalAccessException("Marca no encontrada");
            }

            MarcaRepuesto marca = marcaRepository.findById(id).orElse(null);
            assert marca != null;
            marca.setActivo(activo);
            marcaRepository.save(marca);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error desconocido al activar o desactivar la marca");
        }
    }
}
