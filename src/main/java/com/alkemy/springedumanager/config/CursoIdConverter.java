package com.alkemy.springedumanager.config;

import com.alkemy.springedumanager.model.Curso;
import com.alkemy.springedumanager.repository.CursoRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Permite que un formulario Thymeleaf pueda enviar solo el id de un
 * Curso (por ejemplo desde un &lt;select&gt;) y que Spring MVC lo
 * convierta automaticamente en la entidad completa al hacer el binding.
 */
@Component
public class CursoIdConverter implements Converter<String, Curso> {

    private final CursoRepository cursoRepository;

    public CursoIdConverter(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Curso convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return cursoRepository.findById(Long.valueOf(source)).orElse(null);
    }
}
