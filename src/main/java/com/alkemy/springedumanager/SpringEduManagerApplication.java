package com.alkemy.springedumanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion SpringEduManager.
 *
 * Proyecto de evaluacion del Modulo 6: Desarrollo de aplicaciones JEE con Spring Framework.
 * Permite a la Coordinacion Academica de un bootcamp gestionar estudiantes, cursos
 * y evaluaciones desde una unica plataforma web.
 */
@SpringBootApplication
public class SpringEduManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEduManagerApplication.class, args);
    }
}
