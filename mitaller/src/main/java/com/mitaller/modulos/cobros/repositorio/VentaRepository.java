package com.mitaller.modulos.cobros.repositorio;


import com.mitaller.modulos.cobros.dominio.Venta;
import com.mitaller.modulos.cobros.dto.MesTotalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Venta findByNumeroFactura(String nombre);

    @Query("SELECT new com.mitaller.modulos.cobros.dto.MesTotalDTO(" +
            "CAST(SUBSTRING(v.fecha, 1, 4) AS int), " +
            "CAST(SUBSTRING(v.fecha, 6, 2) AS int), " +
            "SUM(v.total)) " +
            "FROM Venta v " +
            "WHERE v.dadaDeBaja = false " +
            "AND CAST(SUBSTRING(v.fecha, 1, 4) AS int) = :year " +
            "GROUP BY CAST(SUBSTRING(v.fecha, 1, 4) AS int), CAST(SUBSTRING(v.fecha, 6, 2) AS int) " +
            "ORDER BY CAST(SUBSTRING(v.fecha, 1, 4) AS int), CAST(SUBSTRING(v.fecha, 6, 2) AS int)")
    List<MesTotalDTO> findTotalByMonth(@Param("year") int year);


    @Query("SELECT SUM(v.total) FROM Venta v")
    Integer getTotalFacturado();
}
