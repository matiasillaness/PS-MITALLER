package com.mitaller.modulos.vehiculos.repositorio;

import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Vehiculo findByPatenteIgnoreCase(String patente);
}
