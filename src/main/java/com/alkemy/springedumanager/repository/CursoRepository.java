package com.alkemy.springedumanager.repository;

import com.alkemy.springedumanager.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Curso.
 * Leccion 3: Acceso a Datos en Spring Framework.
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByNombreContainingIgnoreCase(String nombre);
}
