package com.alkemy.springedumanager.service;

import com.alkemy.springedumanager.model.Estudiante;
import com.alkemy.springedumanager.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Logica de negocio para la gestion de estudiantes.
 * Leccion 3: Acceso a Datos en Spring Framework (capa de servicio).
 */
@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }

    public boolean existe(Long id) {
        return estudianteRepository.existsById(id);
    }

    public boolean existeEmail(String email) {
        return estudianteRepository.existsByEmail(email);
    }
}
