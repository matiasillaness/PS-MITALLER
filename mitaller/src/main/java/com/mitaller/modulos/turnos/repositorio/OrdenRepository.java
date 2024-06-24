package com.mitaller.modulos.turnos.repositorio;

import com.mitaller.modulos.turnos.dominio.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findAllByOcupada(Boolean ocupada);
}
