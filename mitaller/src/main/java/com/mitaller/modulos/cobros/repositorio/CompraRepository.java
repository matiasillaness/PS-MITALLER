package com.mitaller.modulos.cobros.repositorio;

import com.mitaller.modulos.cobros.dominio.Compra;
import com.mitaller.modulos.cobros.dto.MesTotalDTO;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    Compra findByNombreComprobante(String nombreComprobante);



    @Query("SELECT new com.mitaller.modulos.cobros.dto.MesTotalDTO(" +
            "CAST(SUBSTRING(c.fecha, 1, 4) AS int), " +  // Extrae el año
            "CAST(SUBSTRING(c.fecha, 6, 2) AS int), " +  // Extrae el mes
            "SUM(c.total)) " +
            "FROM Compra c " +
            "WHERE c.dadaDeBaja = false " +
            "AND CAST(SUBSTRING(c.fecha, 1, 4) AS int) = :year " +
            "GROUP BY CAST(SUBSTRING(c.fecha, 1, 4) AS int), CAST(SUBSTRING(c.fecha, 6, 2) AS int) " +
            "ORDER BY CAST(SUBSTRING(c.fecha, 1, 4) AS int), CAST(SUBSTRING(c.fecha, 6, 2) AS int)")
    List<MesTotalDTO> findTotalByMonth(@Param("year") int yea
    );



    @Query("SELECT SUM(v.total) FROM Compra v")
    Integer getTotalGastado();
}



