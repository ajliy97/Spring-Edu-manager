package com.alkemy.springedumanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entidad Evaluacion. Representa la nota que un estudiante obtiene
 * en un curso/practica determinado, consultable desde la plataforma.
 */
@Entity
@Table(name = "evaluaciones")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    @JsonIgnoreProperties({"cursos", "evaluaciones"})
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    @JsonIgnoreProperties({"estudiantes", "evaluaciones"})
    private Curso curso;

    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "0.0", message = "La nota minima es 0")
    @DecimalMax(value = "10.0", message = "La nota maxima es 10")
    private Double nota;

    private String comentario;

    private LocalDate fecha = LocalDate.now();

    public Evaluacion() {
    }

    public Evaluacion(Estudiante estudiante, Curso curso, Double nota, String comentario) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
