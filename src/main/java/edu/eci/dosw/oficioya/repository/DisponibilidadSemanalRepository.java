package edu.eci.dosw.oficioya.repository;

import edu.eci.dosw.oficioya.entity.DisponibilidadSemanal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadSemanalRepository extends JpaRepository<DisponibilidadSemanal, Long> {

    // Buscar todas las disponibilidades de un trabajador
    List<DisponibilidadSemanal> findByTrabajador_Id(Long trabajadorId);

    // Buscar disponibilidades por día de la semana
    List<DisponibilidadSemanal> findByDiaSemana(String diaSemana);

    // Buscar horarios activos de un trabajador
    List<DisponibilidadSemanal> findByTrabajador_IdAndActivo(
            Long trabajadorId,
            Boolean activo
    );
}