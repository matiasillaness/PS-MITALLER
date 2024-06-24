package com.mitaller.modulos.inventario.repositorio;

import com.mitaller.modulos.inventario.dominio.MarcaRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MarcaRepuestoRepository extends JpaRepository<MarcaRepuesto, Long> {
    MarcaRepuesto findByNombre(String nombre);
}
