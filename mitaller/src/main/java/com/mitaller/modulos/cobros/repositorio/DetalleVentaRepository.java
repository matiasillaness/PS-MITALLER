package com.mitaller.modulos.cobros.repositorio;

import com.mitaller.modulos.cobros.dominio.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    @Query("SELECT d.servicio.nombre AS nombre, COUNT(d.servicio) AS cantidad " +
            "FROM DetalleVenta d " +
            "GROUP BY d.servicio.nombre " +
            "ORDER BY COUNT(d.servicio) DESC")
    List<Object[]> findServiciosMasUtilizados();
}
