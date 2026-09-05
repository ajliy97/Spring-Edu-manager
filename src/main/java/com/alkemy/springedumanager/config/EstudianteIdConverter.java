package com.alkemy.springedumanager.config;

import com.alkemy.springedumanager.model.Estudiante;
import com.alkemy.springedumanager.repository.EstudianteRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Permite que un formulario Thymeleaf pueda enviar solo el id de un
 * Estudiante (por ejemplo desde un &lt;select&gt;) y que Spring MVC lo
 * convierta automaticamente en la entidad completa al hacer el binding.
 */
@Component
public class EstudianteIdConverter implements Converter<String, Estudiante> {

    private final EstudianteRepository estudianteRepository;

    public EstudianteIdConverter(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Estudiante convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return estudianteRepository.findById(Long.valueOf(source)).orElse(null);
    }
}
