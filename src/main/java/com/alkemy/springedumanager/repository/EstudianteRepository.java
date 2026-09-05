package com.alkemy.springedumanager.repository;

import com.alkemy.springedumanager.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Estudiante.
 * Leccion 3: Acceso a Datos en Spring Framework.
 */
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByEmail(String email);

    boolean existsByEmail(String email);
}
