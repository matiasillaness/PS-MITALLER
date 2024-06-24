package com.mitaller.modulos.inventario.repositorio;

import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.inventario.dominio.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByNombreContainingIgnoreCase(String nombre);
    List<Servicio> findByPrecioGreaterThan(BigDecimal precio);
    List<Servicio> findByPrecioLessThan(BigDecimal precio);
}
