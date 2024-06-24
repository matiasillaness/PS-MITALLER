package com.mitaller.modulos.usuarios.dominio;


import com.mitaller.modulos.cobros.dominio.Venta;
import com.mitaller.modulos.turnos.dominio.Orden;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Column(name = "direccion")
    private String direccion;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "codigo_cambiar_contrasenia", length = 4)
    private Integer codigoCambiarContrasenia;

    // TODO: ------------------------------------------------

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Orden.class, mappedBy = "usuario")
    private List<Orden> ordenes;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Venta.class, mappedBy = "usuario")
    private List<Venta> ventas;

    // TODO: ------------------------------------------------
    public String getRole() {
        return role.name();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }
    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
       if (activo) {
           return true;
       } else {
           return false;
       }
    }

    @Override
    public boolean isAccountNonLocked() {
        if (activo) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Devuelve true si las credenciales del usuario no han caducado
        // Implementa la lógica necesaria para verificar si las credenciales han caducado
        return true; // O ajusta la lógica según tu aplicación
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
