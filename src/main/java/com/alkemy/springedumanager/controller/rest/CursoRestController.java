package com.alkemy.springedumanager.controller.rest;

import com.alkemy.springedumanager.model.Curso;
import com.alkemy.springedumanager.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API REST para exponer operaciones CRUD sobre cursos.
 * Leccion 5: La Interoperabilidad entre los sistemas.
 *
 * Endpoints:
 *  GET    /api/cursos
 *  GET    /api/cursos/{id}
 *  POST   /api/cursos
 *  PUT    /api/cursos/{id}
 *  DELETE /api/cursos/{id}
 */
@RestController
@RequestMapping("/api/cursos")
public class CursoRestController {

    private final CursoService cursoService;

    @Autowired
    public CursoRestController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtener(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Curso> crear(@Valid @RequestBody Curso curso) {
        Curso creado = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @Valid @RequestBody Curso curso) {
        if (!cursoService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        curso.setId(id);
        return ResponseEntity.ok(cursoService.guardar(curso));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!cursoService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
