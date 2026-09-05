package com.alkemy.springedumanager.controller;

import com.alkemy.springedumanager.model.Curso;
import com.alkemy.springedumanager.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para la gestion de cursos.
 * Leccion 2: El Framework Spring MVC.
 * Leccion 4: Control de acceso mediante Spring Security -> solo los usuarios
 * con rol ADMIN pueden crear, editar o eliminar cursos. Cualquier usuario
 * autenticado puede consultarlos.
 */
@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", cursoService.listarTodos());
        return "cursos/list";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevoFormulario(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Curso curso = cursoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado: " + id));
        model.addAttribute("curso", curso);
        return "cursos/form";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardar(@Valid @ModelAttribute("curso") Curso curso,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cursos/form";
        }
        cursoService.guardar(curso);
        redirectAttributes.addFlashAttribute("mensaje", "Curso guardado correctamente.");
        return "redirect:/cursos";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        cursoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Curso eliminado.");
        return "redirect:/cursos";
    }
}
