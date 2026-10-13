package edu.eci.dosw.oficioya.service;

import edu.eci.dosw.oficioya.exception.CampoObligatorioException;
import edu.eci.dosw.oficioya.exception.TrabajadorNoEncontradoException;

import edu.eci.dosw.oficioya.mapper.TrabajadorMapper;
import edu.eci.dosw.oficioya.mapper.UsuarioMapper;

import edu.eci.dosw.oficioya.model.Trabajador;

import edu.eci.dosw.oficioya.repository.TrabajadorRepository;
import edu.eci.dosw.oficioya.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrabajadorMapper trabajadorMapper;
    private final UsuarioMapper usuarioMapper;

    // Inyección de dependencias por constructor
    public TrabajadorServiceImpl(
            TrabajadorRepository trabajadorRepository,
            UsuarioRepository usuarioRepository,
            TrabajadorMapper trabajadorMapper,
            UsuarioMapper usuarioMapper) {

        this.trabajadorRepository = trabajadorRepository;
        this.usuarioRepository = usuarioRepository;
        this.trabajadorMapper = trabajadorMapper;
        this.usuarioMapper = usuarioMapper;
    }

    // LISTAR TRABAJADORES
    @Override
    @Transactional(readOnly = true)
    public List<Trabajador> listar() {

        return trabajadorRepository.findAll()
                .stream()
                .filter(t -> !Boolean.TRUE.equals(t.getEliminadoLogico()))
                .map(this::convertirAModelo)
                .toList();
    }

    // OBTENER TRABAJADOR POR ID
    @Override
    @Transactional(readOnly = true)
    public Trabajador obtenerPorId(Long id) {

        var entidad = trabajadorRepository.findById(id)
                .orElseThrow(() ->
                        new TrabajadorNoEncontradoException(id)
                );

        return convertirAModelo(entidad);
    }

    // CREAR TRABAJADOR
    @Override
    public Trabajador crear(Trabajador trabajador) {

        validarCamposObligatorios(trabajador);

        String correo = trabajador.getUsuario().getCorreo();

        // Verificar que el correo no esté registrado
        if (usuarioRepository.existsByEmail(correo)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario con este correo"
            );
        }

        // Convertir usuario de modelo a entidad
        var usuarioEntidad =
                usuarioMapper.toEntity(trabajador.getUsuario());

        usuarioEntidad.setId(null);

        // Valor temporal para las pruebas locales del laboratorio.
        // No utilizar contraseñas reales.
        usuarioEntidad.setPasswordHash(
                trabajador.getUsuario().getContrasena()
        );

        usuarioEntidad.setEstadoCuenta("Activo");
        usuarioEntidad.setEliminadoLogico(false);
        usuarioEntidad.setFechaRegistro(LocalDateTime.now());

        // Guardar usuario en PostgreSQL
        usuarioEntidad = usuarioRepository.save(usuarioEntidad);

        // Convertir trabajador de modelo a entidad
        var trabajadorEntidad =
                trabajadorMapper.toEntity(trabajador);

        trabajadorEntidad.setId(null);
        trabajadorEntidad.setUsuario(usuarioEntidad);

        trabajadorEntidad.setEstado("Activo");
        trabajadorEntidad.setEliminadoLogico(false);

        trabajadorEntidad.setFechaCreacion(LocalDateTime.now());
        trabajadorEntidad.setFechaActualizacion(LocalDateTime.now());

        // Guardar trabajador en PostgreSQL
        trabajadorEntidad =
                trabajadorRepository.save(trabajadorEntidad);

        return convertirAModelo(trabajadorEntidad);
    }

    // ACTUALIZAR TRABAJADOR
    @Override
    public Trabajador actualizar(Long id, Trabajador datosNuevos) {

        var existente = trabajadorRepository.findById(id)
                .orElseThrow(() ->
                        new TrabajadorNoEncontradoException(id)
                );

        if (Boolean.TRUE.equals(existente.getEliminadoLogico())) {
            throw new IllegalStateException(
                    "No se puede modificar un trabajador inactivo"
            );
        }

        validarActualizacion(datosNuevos);

        // Convertir los datos nuevos utilizando MapStruct
        var datosEntidad =
                trabajadorMapper.toEntity(datosNuevos);

        existente.setNombreCompleto(
                datosEntidad.getNombreCompleto()
        );

        existente.setFotoPerfilUrl(
                datosEntidad.getFotoPerfilUrl()
        );

        existente.setTarifaAproximada(
                datosEntidad.getTarifaAproximada()
        );

        existente.setDisponibleAhora(
                datosEntidad.getDisponibleAhora()
        );

        existente.setPenalizacionAcumulada(
                datosEntidad.getPenalizacionAcumulada()
        );

        existente.setFechaActualizacion(LocalDateTime.now());

        // Guardar cambios en PostgreSQL
        return convertirAModelo(
                trabajadorRepository.save(existente)
        );
    }

    // DESACTIVAR TRABAJADOR
    @Override
    public Trabajador desactivar(Long id) {

        var entidad = trabajadorRepository.findById(id)
                .orElseThrow(() ->
                        new TrabajadorNoEncontradoException(id)
                );

        entidad.setEliminadoLogico(true);
        entidad.setEstado("Inactivo");
        entidad.setFechaActualizacion(LocalDateTime.now());

        return convertirAModelo(
                trabajadorRepository.save(entidad)
        );
    }

    // BUSCAR TRABAJADOR POR CORREO
    @Override
    @Transactional(readOnly = true)
    public Optional<Trabajador> buscarPorCorreo(String correo) {

        return trabajadorRepository
                .findByUsuario_EmailIgnoreCase(correo)
                .filter(t ->
                        !Boolean.TRUE.equals(t.getEliminadoLogico())
                )
                .map(this::convertirAModelo);
    }

    // CONVERTIR ENTIDAD A MODELO
    private Trabajador convertirAModelo(
            edu.eci.dosw.oficioya.entity.Trabajador entidad) {

        Trabajador modelo = trabajadorMapper.toModel(entidad);

        // El mapper ignora la relación con Usuario,
        // por eso la convertimos por separado.
        if (entidad.getUsuario() != null) {

            modelo.setUsuario(
                    usuarioMapper.toModel(entidad.getUsuario())
            );
        }

        return modelo;
    }

    // VALIDACIONES PARA CREAR
    private void validarCamposObligatorios(Trabajador trabajador) {

        if (trabajador == null) {
            throw new CampoObligatorioException("trabajador");
        }

        if (isVacio(trabajador.getNombreCompleto())) {
            throw new CampoObligatorioException("nombre");
        }

        if (trabajador.getUsuario() == null) {
            throw new CampoObligatorioException("usuario");
        }

        if (isVacio(trabajador.getUsuario().getCorreo())) {
            throw new CampoObligatorioException("correo");
        }

        if (isVacio(trabajador.getUsuario().getTelefono())) {
            throw new CampoObligatorioException("telefono");
        }

        if (isVacio(trabajador.getUsuario().getContrasena())) {
            throw new CampoObligatorioException("contrasena");
        }

        if (trabajador.getOficios() == null ||
                trabajador.getOficios().isEmpty()) {

            throw new CampoObligatorioException("oficio principal");
        }
    }

    // VALIDACIONES PARA ACTUALIZAR
    private void validarActualizacion(Trabajador trabajador) {

        if (trabajador == null) {
            throw new CampoObligatorioException("trabajador");
        }

        if (isVacio(trabajador.getNombreCompleto())) {
            throw new CampoObligatorioException("nombre");
        }
    }

    private boolean isVacio(String valor) {
        return valor == null || valor.isBlank();
    }
}