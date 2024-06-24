package com.mitaller.modulos.vehiculos.repositorio;

import com.mitaller.modulos.vehiculos.dominio.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Marca findByNombre(String nombre);


    
}
