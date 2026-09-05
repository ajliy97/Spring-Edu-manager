package com.alkemy.springedumanager.controller.rest;

import com.alkemy.springedumanager.model.Estudiante;
import com.alkemy.springedumanager.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API REST para exponer operaciones CRUD sobre estudiantes.
 * Leccion 5: La Interoperabilidad entre los sistemas.
 *
 * Endpoints:
 *  GET    /api/estudiantes
 *  GET    /api/estudiantes/{id}
 *  POST   /api/estudiantes
 *  PUT    /api/estudiantes/{id}
 *  DELETE /api/estudiantes/{id}
 */
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteRestController {

    private final EstudianteService estudianteService;

    @Autowired
    public EstudianteRestController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<Estudiante> listar() {
        return estudianteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtener(@PathVariable Long id) {
        return estudianteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estudiante> crear(@Valid @RequestBody Estudiante estudiante) {
        Estudiante creado = estudianteService.guardar(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id,
                                                  @Valid @RequestBody Estudiante estudiante) {
        if (!estudianteService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        estudiante.setId(id);
        return ResponseEntity.ok(estudianteService.guardar(estudiante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!estudianteService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
