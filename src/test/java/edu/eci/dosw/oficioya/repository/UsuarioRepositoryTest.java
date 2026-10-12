package edu.eci.dosw.oficioya.repository;

import edu.eci.dosw.oficioya.entity.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear un usuario válido para las pruebas
    private Usuario crearUsuario(String correo) {

        Usuario usuario = new Usuario();

        usuario.setEmail(correo);
        usuario.setPasswordHash("contrasenaPrueba");
        usuario.setEstadoCuenta("ACTIVO");
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEliminadoLogico(false);

        return usuario;
    }

    // PRUEBA 1: GUARDAR Y CONSULTAR
    @Test
    void deberiaGuardarYBuscarUsuarioPorCorreo() {

        Usuario usuario = crearUsuario("prueba@oficioya.com");

        usuarioRepository.saveAndFlush(usuario);

        Optional<Usuario> resultado =
                usuarioRepository.findByEmail("prueba@oficioya.com");

        assertTrue(resultado.isPresent());
        assertEquals("prueba@oficioya.com", resultado.get().getEmail());
    }

    // PRUEBA 2: ACTUALIZAR
    @Test
    void deberiaActualizarCorreoUsuario() {

        Usuario usuario = crearUsuario("original@oficioya.com");

        Usuario guardado = usuarioRepository.saveAndFlush(usuario);

        guardado.setEmail("actualizado@oficioya.com");

        usuarioRepository.saveAndFlush(guardado);

        Optional<Usuario> resultado =
                usuarioRepository.findByEmail("actualizado@oficioya.com");

        assertTrue(resultado.isPresent());
        assertEquals("actualizado@oficioya.com", resultado.get().getEmail());
    }

    // PRUEBA 3: ELIMINAR
    @Test
    void deberiaEliminarUsuario() {

        Usuario usuario = crearUsuario("eliminar@oficioya.com");

        Usuario guardado = usuarioRepository.saveAndFlush(usuario);

        Long id = guardado.getId();

        usuarioRepository.delete(guardado);
        usuarioRepository.flush();

        Optional<Usuario> resultado = usuarioRepository.findById(id);

        assertTrue(resultado.isEmpty());
    }

    // PRUEBA 4: VERIFICAR EXISTENCIA POR CORREO
    @Test
    void deberiaVerificarSiExisteCorreo() {

        Usuario usuario = crearUsuario("existe@oficioya.com");

        usuarioRepository.saveAndFlush(usuario);

        assertTrue(
                usuarioRepository.existsByEmail("existe@oficioya.com")
        );

        assertFalse(
                usuarioRepository.existsByEmail("noexiste@oficioya.com")
        );
    }
}