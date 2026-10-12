package edu.eci.dosw.oficioya.repository;

import edu.eci.dosw.oficioya.entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    // Buscar trabajadores por nombre sin distinguir mayúsculas
    List<Trabajador> findByNombreCompletoContainingIgnoreCase(String nombre);

    // Buscar trabajadores por estado
    List<Trabajador> findByEstado(String estado);

    // Buscar trabajadores disponibles actualmente
    List<Trabajador> findByDisponibleAhora(Boolean disponibleAhora);

    // Buscar el perfil de trabajador asociado a un usuario
    Optional<Trabajador> findByUsuario_Id(Long usuarioId);
}