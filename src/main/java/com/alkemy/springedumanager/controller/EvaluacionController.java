package com.alkemy.springedumanager.controller;

import com.alkemy.springedumanager.model.Evaluacion;
import com.alkemy.springedumanager.service.CursoService;
import com.alkemy.springedumanager.service.EstudianteService;
import com.alkemy.springedumanager.service.EvaluacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para consultar y registrar evaluaciones de los estudiantes.
 * Los estudiantes/usuarios pueden consultar sus evaluaciones; solo un ADMIN
 * puede cargar nuevas notas.
 */
@Controller
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    @Autowired
    public EvaluacionController(EvaluacionService evaluacionService,
                                 EstudianteService estudianteService,
                                 CursoService cursoService) {
        this.evaluacionService = evaluacionService;
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("evaluaciones", evaluacionService.listarTodas());
        return "evaluaciones/list";
    }

    @GetMapping("/nueva")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevaFormulario(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "evaluaciones/form";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardar(@Valid @ModelAttribute("evaluacion") Evaluacion evaluacion,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", estudianteService.listarTodos());
            model.addAttribute("cursos", cursoService.listarTodos());
            return "evaluaciones/form";
        }
        evaluacionService.guardar(evaluacion);
        redirectAttributes.addFlashAttribute("mensaje", "Evaluacion registrada correctamente.");
        return "redirect:/evaluaciones";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        evaluacionService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Evaluacion eliminada.");
        return "redirect:/evaluaciones";
    }
}
