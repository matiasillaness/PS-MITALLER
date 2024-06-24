package com.mitaller.modulos.usuarios.repositorio;

import com.mitaller.modulos.usuarios.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmailIgnoreCase(String email);
}
