package com.alkemy.springedumanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad Curso. Representa un curso o practica del bootcamp en el que
 * pueden inscribirse varios estudiantes.
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    private String docente;

    @ManyToMany(mappedBy = "cursos")
    @JsonIgnoreProperties("cursos")
    private Set<Estudiante> estudiantes = new HashSet<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("curso")
    private Set<Evaluacion> evaluaciones = new HashSet<>();

    public Curso() {
    }

    public Curso(String nombre, String descripcion, String docente) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.docente = docente;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Set<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(Set<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}
