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

| Usuario | Contraseña | Rol         |
| ------- | ---------- | ----------- |
| admin   | admin123   | ADMIN, USER |
| user    | user123    | USER        |

Solo el usuario **admin** puede crear/editar/eliminar cursos y registrar
evaluaciones. Cualquier usuario (o estudiante) autenticado puede consultar cursos, evaluaciones y estudiantes (agregar estudiante). Los endpoints `GET /api/**` están abiertos para integraciones externas; los `POST/PUT/DELETE` de cursos y evaluaciones
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
