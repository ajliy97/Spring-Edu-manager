package com.alkemy.springedumanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracion de seguridad de la aplicacion.
 * Leccion 4: Control de acceso mediante Spring Security.
 *
 * - Usuarios en memoria con roles ADMIN y USER (equivalente a los
 *   "usuarios en application.properties" solicitados en la consigna,
 *   definidos aqui de forma tipada para poder asignarles un PasswordEncoder).
 * - Rutas de creacion/edicion/borrado de cursos y evaluaciones restringidas
 *   a ADMIN mediante @PreAuthorize (ver EnableMethodSecurity) y tambien
 *   reforzadas a nivel de HttpSecurity.
 * - Formulario de login y logout funcional.
 * - Consola H2 y API REST habilitadas para pruebas con Postman/RestTemplate.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN", "USER")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Recursos publicos
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/h2-console/**").permitAll()
                // Lectura de la API REST abierta para integraciones externas
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/**").permitAll()
                // Editar/eliminar estudiantes es exclusivo de ADMIN; crear y
                // ver el listado queda abierto a cualquier usuario autenticado
                .requestMatchers("/estudiantes/editar/**", "/estudiantes/eliminar/**")
                    .hasRole("ADMIN")
                // Escritura de la API REST protegida por rol
                .requestMatchers("/cursos/nuevo", "/cursos/guardar", "/cursos/editar/**", "/cursos/eliminar/**")
                    .hasRole("ADMIN")
                .requestMatchers("/evaluaciones/nueva", "/evaluaciones/guardar", "/evaluaciones/eliminar/**")
                    .hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // Se deshabilita CSRF solo para la consola H2 y la API REST
            // (pensada para ser consumida por clientes externos como Postman)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"))
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            // Permite autenticacion basica ademas del login por formulario,
            // util para probar la API REST desde Postman/RestTemplate.
            .httpBasic(org.springframework.security.config.Customizer.withDefaults());

        return http.build();
    }
}
