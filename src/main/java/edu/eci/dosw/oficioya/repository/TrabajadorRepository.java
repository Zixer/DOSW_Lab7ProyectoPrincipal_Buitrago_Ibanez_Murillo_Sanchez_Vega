package edu.eci.dosw.oficioya.repository;

import edu.eci.dosw.oficioya.entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    List<Trabajador> findByNombreCompletoContainingIgnoreCase(String nombre);

    List<Trabajador> findByEstado(String estado);

    List<Trabajador> findByDisponibleAhora(Boolean disponibleAhora);

    Optional<Trabajador> findByUsuario_Id(Long usuarioId);

    Optional<Trabajador> findByUsuario_EmailIgnoreCase(String email);
}