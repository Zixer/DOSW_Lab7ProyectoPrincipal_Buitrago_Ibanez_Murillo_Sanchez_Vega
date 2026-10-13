package edu.eci.dosw.oficioya.service;

import edu.eci.dosw.oficioya.dto.AuthRequest;
import edu.eci.dosw.oficioya.dto.AuthResponse;

import edu.eci.dosw.oficioya.exception.CredencialesInvalidasException;

import edu.eci.dosw.oficioya.mapper.UsuarioMapper;

import edu.eci.dosw.oficioya.model.Usuario;

import edu.eci.dosw.oficioya.repository.UsuarioRepository;
import edu.eci.dosw.oficioya.repository.TrabajadorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final UsuarioMapper usuarioMapper;

    // Inyección de dependencias por constructor
    public AuthServiceImpl(
            UsuarioRepository usuarioRepository,
            TrabajadorRepository trabajadorRepository,
            UsuarioMapper usuarioMapper) {

        this.usuarioRepository = usuarioRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse autenticar(AuthRequest request) {

        // Buscar usuario en PostgreSQL
        var usuarioEntidad = usuarioRepository
                .findByEmail(request.getCorreo())
                .orElseThrow(CredencialesInvalidasException::new);

        // Verificar que la cuenta esté activa
        if (Boolean.TRUE.equals(usuarioEntidad.getEliminadoLogico())
                || !"Activo".equalsIgnoreCase(
                        usuarioEntidad.getEstadoCuenta())) {

            throw new CredencialesInvalidasException();
        }

        // Comparar contraseña para las pruebas locales del laboratorio.
        // No utilizar credenciales reales.
        if (usuarioEntidad.getPasswordHash() == null
                || !usuarioEntidad.getPasswordHash()
                        .equals(request.getContrasena())) {

            throw new CredencialesInvalidasException();
        }

        // Buscar trabajador asociado al usuario
        var trabajadorEntidad = trabajadorRepository
                .findByUsuario_Id(usuarioEntidad.getId())
                .orElseThrow(CredencialesInvalidasException::new);

        // Verificar que el trabajador esté activo
        if (Boolean.TRUE.equals(
                trabajadorEntidad.getEliminadoLogico())) {

            throw new CredencialesInvalidasException();
        }

        // Convertir entidad a modelo usando MapStruct
        Usuario usuarioModelo =
                usuarioMapper.toModel(usuarioEntidad);

        return new AuthResponse(
                "Autenticación exitosa",
                usuarioModelo.getCorreo(),
                trabajadorEntidad.getNombreCompleto()
        );
    }
}