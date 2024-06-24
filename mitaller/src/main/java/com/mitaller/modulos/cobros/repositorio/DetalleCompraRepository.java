package com.mitaller.modulos.cobros.repositorio;

import com.mitaller.modulos.cobros.dominio.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
}
