package com.alkemy.springedumanager.repository;

import com.alkemy.springedumanager.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Evaluacion.
 * Leccion 3: Acceso a Datos en Spring Framework.
 */
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByEstudianteId(Long estudianteId);

    List<Evaluacion> findByCursoId(Long cursoId);
}
