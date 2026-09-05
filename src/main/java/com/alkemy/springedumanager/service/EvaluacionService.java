package com.alkemy.springedumanager.service;

import com.alkemy.springedumanager.model.Evaluacion;
import com.alkemy.springedumanager.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Logica de negocio para la gestion de evaluaciones.
 * Leccion 3: Acceso a Datos en Spring Framework (capa de servicio).
 */
@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    @Autowired
    public EvaluacionService(EvaluacionRepository evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    public List<Evaluacion> listarTodas() {
        return evaluacionRepository.findAll();
    }

    public List<Evaluacion> listarPorEstudiante(Long estudianteId) {
        return evaluacionRepository.findByEstudianteId(estudianteId);
    }

    public List<Evaluacion> listarPorCurso(Long cursoId) {
        return evaluacionRepository.findByCursoId(cursoId);
    }

    public Optional<Evaluacion> buscarPorId(Long id) {
        return evaluacionRepository.findById(id);
    }

    public Evaluacion guardar(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public void eliminar(Long id) {
        evaluacionRepository.deleteById(id);
    }
}
