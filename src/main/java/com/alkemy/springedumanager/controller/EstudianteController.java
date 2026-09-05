package com.alkemy.springedumanager.controller;

import com.alkemy.springedumanager.model.Curso;
import com.alkemy.springedumanager.model.Estudiante;
import com.alkemy.springedumanager.service.CursoService;
import com.alkemy.springedumanager.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controlador MVC para la gestion de estudiantes: registro, listado,
 * edicion y baja desde la interfaz web.
 * Leccion 2: El Framework Spring MVC.
 */
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    @Autowired
    public EstudianteController(EstudianteService estudianteService, CursoService cursoService) {
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "estudiantes/list";
    }

    /**
     * Solo un usuario con rol USER o ADMIN puede registrar un nuevo
     * estudiante (formulario de alta).
     */
    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "estudiantes/form";
    }

    /**
     * Editar un estudiante existente queda reservado solo para ADMIN.
     */
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Estudiante estudiante = estudianteService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado: " + id));
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("cursos", cursoService.listarTodos());
        return "estudiantes/form";
    }

    /**
     * Este endpoint atiende tanto el alta (id nulo) como la edicion
     * (id presente) porque reutiliza el mismo formulario. Se permite
     * crear a cualquier usuario autenticado, pero si el formulario trae
     * un id (edicion) se exige rol ADMIN, para que un usuario con rol
     * USER no pueda editar un estudiante manipulando el campo oculto.
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("estudiante") Estudiante estudiante,
                           BindingResult result,
                           @RequestParam(value = "cursoIds", required = false) List<Long> cursoIds,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (estudiante.getId() != null && !esAdmin()) {
            throw new AccessDeniedException("Solo un administrador puede editar estudiantes.");
        }

        if (result.hasErrors()) {
            model.addAttribute("cursos", cursoService.listarTodos());
            return "estudiantes/form";
        }

        Set<Curso> cursosSeleccionados = new HashSet<>();
        if (cursoIds != null) {
            for (Long cursoId : cursoIds) {
                cursoService.buscarPorId(cursoId).ifPresent(cursosSeleccionados::add);
            }
        }
        estudiante.setCursos(cursosSeleccionados);

        estudianteService.guardar(estudiante);
        redirectAttributes.addFlashAttribute("mensaje", "Estudiante guardado correctamente.");
        return "redirect:/estudiantes";
    }

    /**
     * Eliminar un estudiante queda reservado solo para ADMIN.
     */
    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        estudianteService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Estudiante eliminado.");
        return "redirect:/estudiantes";
    }

    private boolean esAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
