package com.mitaller.modulos.inventario.repositorio;

import com.mitaller.modulos.inventario.dominio.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
    List<Repuesto> findByActivo(boolean activo);
    List<Repuesto> findByPrecioGreaterThan(BigDecimal precio);
    List<Repuesto> findByPrecioLessThan(BigDecimal precio);
    List<Repuesto> findByNombreContaining(String nombre);

    List<Repuesto> findByPrecio(BigDecimal precio);
    List<Repuesto> findByStock(int stock);
}
