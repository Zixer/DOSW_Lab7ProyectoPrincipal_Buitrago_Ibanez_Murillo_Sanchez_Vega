package edu.eci.dosw.oficioya.repository;

import edu.eci.dosw.oficioya.entity.Usuario;
import edu.eci.dosw.oficioya.entity.Trabajador;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TrabajadorRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Test
    void deberiaGuardarRelacionUsuarioTrabajador() {

        Usuario usuario = new Usuario();

        usuario.setEmail("trabajador@oficioya.com");
        usuario.setPasswordHash("contrasenaPrueba");
        usuario.setTelefono("3001234567");
        usuario.setEstadoCuenta("ACTIVO");
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEliminadoLogico(false);

        Usuario usuarioGuardado = usuarioRepository.saveAndFlush(usuario);

        Trabajador trabajador = new Trabajador();

        trabajador.setUsuario(usuarioGuardado);
        trabajador.setNombreCompleto("Juan Perez");
        trabajador.setFotoPerfilUrl(null);
        trabajador.setTarifaAproximada(new BigDecimal("50000.00"));
        trabajador.setDisponibleAhora(true);
        trabajador.setEstado("ACTIVO");
        trabajador.setPenalizacionAcumulada(0.0);
        trabajador.setFechaCreacion(LocalDateTime.now());
        trabajador.setEliminadoLogico(false);

        // 4. Guardar el trabajador en H2
        Trabajador trabajadorGuardado =
                trabajadorRepository.saveAndFlush(trabajador);

        Optional<Trabajador> resultado =
                trabajadorRepository.findById(trabajadorGuardado.getId());

        assertTrue(resultado.isPresent());

        assertNotNull(resultado.get().getUsuario());

        assertEquals(
                usuarioGuardado.getId(),
                resultado.get().getUsuario().getId()
        );

        assertEquals(
                "Juan Perez",
                resultado.get().getNombreCompleto()
        );
    }
}