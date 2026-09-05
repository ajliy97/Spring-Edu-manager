package com.alkemy.springedumanager.service;

import com.alkemy.springedumanager.model.Curso;
import com.alkemy.springedumanager.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Logica de negocio para la gestion de cursos.
 * Leccion 3: Acceso a Datos en Spring Framework (capa de servicio).
 */
@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    public boolean existe(Long id) {
        return cursoRepository.existsById(id);
    }
}
