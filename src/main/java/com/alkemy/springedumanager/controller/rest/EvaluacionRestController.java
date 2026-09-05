package com.alkemy.springedumanager.controller.rest;

import com.alkemy.springedumanager.model.Evaluacion;
import com.alkemy.springedumanager.service.EvaluacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API REST de solo consulta (y alta protegida) para las evaluaciones.
 * Permite a un sistema externo consultar las notas de estudiantes y cursos.
 * Leccion 5: La Interoperabilidad entre los sistemas.
 */
@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionRestController {

    private final EvaluacionService evaluacionService;

    @Autowired
    public EvaluacionRestController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public List<Evaluacion> listar() {
        return evaluacionService.listarTodas();
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<Evaluacion> porEstudiante(@PathVariable Long estudianteId) {
        return evaluacionService.listarPorEstudiante(estudianteId);
    }

    @GetMapping("/curso/{cursoId}")
    public List<Evaluacion> porCurso(@PathVariable Long cursoId) {
        return evaluacionService.listarPorCurso(cursoId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Evaluacion> crear(@Valid @RequestBody Evaluacion evaluacion) {
        Evaluacion creada = evaluacionService.guardar(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        evaluacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
