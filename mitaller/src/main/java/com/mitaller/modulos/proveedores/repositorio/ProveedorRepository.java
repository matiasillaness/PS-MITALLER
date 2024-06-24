package com.mitaller.modulos.proveedores.repositorio;

import com.mitaller.modulos.proveedores.dominio.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Proveedor findByIdProveedor(Long idProveedor);
}
