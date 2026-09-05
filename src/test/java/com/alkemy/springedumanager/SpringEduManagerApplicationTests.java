package com.alkemy.springedumanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Verifica que el contexto de Spring se levante correctamente con
 * todas las configuraciones (JPA, Security, MVC) integradas.
 */
@SpringBootTest
class SpringEduManagerApplicationTests {

    @Test
    void contextLoads() {
        // Si el contexto de Spring no carga, este test falla.
    }
}
