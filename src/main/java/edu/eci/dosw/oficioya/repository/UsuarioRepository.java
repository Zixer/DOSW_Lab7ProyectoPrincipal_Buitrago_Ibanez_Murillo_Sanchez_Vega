package edu.eci.dosw.oficioya.repository;

import edu.eci.dosw.oficioya.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar un usuario por correo electrónico
    Optional<Usuario> findByEmail(String email);

    // Verificar si ya existe un correo registrado
    boolean existsByEmail(String email);

    // Buscar usuarios según el estado de su cuenta
    List<Usuario> findByEstadoCuentaAndEliminadoLogico(
            String estadoCuenta,
            Boolean eliminadoLogico
    );
}