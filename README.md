# SpringEduManager

Proyecto de evaluación del **Módulo 6: Desarrollo de aplicaciones JEE con Spring
Framework**. Aplicación web que permite a un bootcamp de programación gestionar
**estudiantes**, **cursos** y **evaluaciones** desde una sola plataforma, con
vistas web (Spring MVC + Thymeleaf), persistencia (Spring Data JPA), seguridad
(Spring Security) y una API REST.

## Stack utilizado

- Java 17
- Maven
- Spring Boot 3.3.4
- Spring MVC + Thymeleaf
- Spring Data JPA + H2 (embebida) / MySQL opcional
- Spring Security (usuarios en memoria con roles ADMIN/USER)
- API RESTful (`@RestController`)

## Cómo ejecutar el proyecto

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

Consola H2 (para inspeccionar la base de datos embebida): `http://localhost:8080/h2-console`
JDBC URL: `jdbc:h2:mem:springedumanager`, usuario `sa`, sin contraseña.

### Usuarios de prueba (Spring Security)

| Usuario | Contraseña | Rol            |
|---------|------------|----------------|
| admin   | admin123   | ADMIN, USER    |
| user    | user123    | USER           |

Solo el usuario **admin** puede crear/editar/eliminar cursos y registrar
evaluaciones. Cualquier usuario autenticado puede gestionar estudiantes y
consultar cursos y evaluaciones. Los endpoints `GET /api/**` están abiertos
para integraciones externas; los `POST/PUT/DELETE` de cursos y evaluaciones
requieren rol ADMIN (autenticación básica o de formulario).

## Estructura del proyecto

```
src/main/java/com/alkemy/springedumanager/
├── SpringEduManagerApplication.java   # Punto de entrada (Lección 1)
├── model/                             # Entidades JPA (Lección 2 y 3)
│   ├── Estudiante.java
│   ├── Curso.java
│   └── Evaluacion.java
├── repository/                        # Repositorios JPA (Lección 3)
│   ├── EstudianteRepository.java
│   ├── CursoRepository.java
│   └── EvaluacionRepository.java
├── service/                           # Lógica de negocio (@Service)
│   ├── EstudianteService.java
│   ├── CursoService.java
│   └── EvaluacionService.java
├── controller/                        # Controladores MVC (Lección 2)
│   ├── HomeController.java
│   ├── EstudianteController.java
│   ├── CursoController.java           # Protegido con @PreAuthorize (Lección 4)
│   ├── EvaluacionController.java
│   ├── GlobalExceptionHandler.java
│   └── rest/                          # API REST (Lección 5)
│       ├── EstudianteRestController.java
│       ├── CursoRestController.java
│       └── EvaluacionRestController.java
└── config/
    ├── SecurityConfig.java            # Spring Security (Lección 4)
    ├── EstudianteIdConverter.java
    └── CursoIdConverter.java

src/main/resources/
├── application.properties
├── data.sql                           # Datos de ejemplo
├── static/css/estilos.css
└── templates/                         # Vistas Thymeleaf
    ├── index.html, login.html, error.html, fragments.html
    ├── estudiantes/{list,form}.html
    ├── cursos/{list,form}.html
    └── evaluaciones/{list,form}.html
```

## Cobertura por lección

### Lección 1 — El gestor de proyectos
- Proyecto Maven creado con `pom.xml` (equivalente a generarlo desde
  start.spring.io) con las dependencias: `spring-boot-starter-web`,
  `spring-boot-starter-data-jpa`, `spring-boot-starter-security`, `h2`,
  `mysql-connector-j` (alternativa), `spring-boot-starter-thymeleaf`,
  `spring-boot-starter-validation`.
- El ciclo de vida se verifica con `mvn clean`, `mvn install` y `mvn package`.
- Listo para subir a GitHub (incluye `.gitignore`).

### Lección 2 — El Framework Spring MVC
- Entidades `Estudiante` y `Curso` con sus controladores (`EstudianteController`,
  `CursoController`) y vistas Thymeleaf.
- Formularios HTML (Thymeleaf) para ingresar estudiantes y cursos.
- Rutas con `@Controller`, `@GetMapping`, `@PostMapping`.
- Listado de cursos y estudiantes visible en la interfaz (`/estudiantes`, `/cursos`).

### Lección 3 — Acceso a Datos en Spring Framework
- Repositorios `EstudianteRepository`, `CursoRepository`, `EvaluacionRepository`
  que extienden `JpaRepository`.
- Base de datos embebida H2 configurada en `application.properties` (con bloque
  comentado listo para MySQL).
- Capa `@Service` (`EstudianteService`, `CursoService`, `EvaluacionService`)
  sobre los repositorios `@Repository`.
- Los formularios de la Lección 2 ya persisten y consultan datos reales.

### Lección 4 — Control de acceso mediante Spring Security
- Dependencia `spring-boot-starter-security` agregada.
- Usuarios definidos (equivalente a `application.properties`) en
  `SecurityConfig` con `BCryptPasswordEncoder`.
- Rutas protegidas por rol con `@PreAuthorize("hasRole('ADMIN')")` en
  `CursoController`, `EvaluacionController` y sus equivalentes REST.
- Formulario de login (`/login`) y logout funcional (`/logout`).
- Solo ADMIN puede ingresar nuevos cursos y evaluaciones.

### Lección 5 — La interoperabilidad entre los sistemas
- Controladores `@RestController` (`EstudianteRestController`,
  `CursoRestController`, `EvaluacionRestController`) que exponen datos en JSON.
- Operaciones CRUD completas con `@GetMapping`, `@PostMapping`, `@PutMapping`,
  `@DeleteMapping`.
- Pensado para probarse desde Postman o un cliente `RestTemplate` externo.
- JWT queda como mejora opcional (plus) no implementada en esta entrega; la
  API REST actualmente se protege con Spring Security (Basic Auth / sesión).

## Qué se valida (checklist de entrega)

- [x] Estructura y modularización según buenas prácticas (capas model /
      repository / service / controller).
- [x] Uso adecuado de Maven y configuración de dependencias.
- [x] Implementación funcional del patrón MVC.
- [x] Persistencia y manipulación de datos con JPA (H2, con alternativa MySQL).
- [x] Configuración segura con roles y login/logout.
- [x] Servicios RESTful con respuesta en JSON.
- [x] Coherencia entre entregas (todas las lecciones integradas en un único proyecto).

## Entregables sugeridos (según la consigna)

- Repositorio Git con el proyecto completo y este `README.md`.
- Capturas de pantalla o video breve mostrando: login, listado de estudiantes/
  cursos/evaluaciones, alta de un curso como ADMIN, y una petición a la API
  REST desde Postman.
- Enlace al repositorio en tu portafolio y en tu CV.
