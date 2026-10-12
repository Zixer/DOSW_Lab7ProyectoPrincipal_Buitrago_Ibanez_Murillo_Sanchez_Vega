package edu.eci.dosw.oficioya.repository;

import edu.eci.dosw.oficioya.entity.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deberiaGuardarYBuscarUsuarioPorCorreo() {

        Usuario usuario = new Usuario();

        usuario.setEmail("prueba@oficioya.com");
        usuario.setPasswordHash("contrasenaPrueba");
        usuario.setEstadoCuenta("ACTIVO");
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEliminadoLogico(false);

        usuarioRepository.save(usuario);

        Optional<Usuario> resultado =
                usuarioRepository.findByEmail("prueba@oficioya.com");

        assertTrue(resultado.isPresent());
        assertEquals("prueba@oficioya.com", resultado.get().getEmail());
    }
}