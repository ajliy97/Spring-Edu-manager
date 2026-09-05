-- Datos de ejemplo para probar la aplicacion apenas arranca.

INSERT INTO cursos (id, nombre, descripcion, docente) VALUES
  (1, 'Java EE con Spring', 'Desarrollo de aplicaciones JEE usando el ecosistema Spring', 'Prof. Garcia'),
  (2, 'Bases de Datos', 'Modelado relacional y SQL aplicado', 'Prof. Lopez');

INSERT INTO estudiantes (id, nombre, apellido, email, fecha_registro) VALUES
  (1, 'Ana', 'Perez', 'ana.perez@example.com', CURRENT_DATE),
  (2, 'Luis', 'Fernandez', 'luis.fernandez@example.com', CURRENT_DATE);

INSERT INTO estudiante_curso (estudiante_id, curso_id) VALUES
  (1, 1),
  (1, 2),
  (2, 1);

INSERT INTO evaluaciones (id, estudiante_id, curso_id, nota, comentario, fecha) VALUES
  (1, 1, 1, 9.0, 'Excelente manejo de Spring Boot y Spring Security', CURRENT_DATE),
  (2, 2, 1, 7.5, 'Buen desempeno, reforzar JPA', CURRENT_DATE);

ALTER TABLE cursos ALTER COLUMN id RESTART WITH 3;
ALTER TABLE estudiantes ALTER COLUMN id RESTART WITH 3;
ALTER TABLE evaluaciones ALTER COLUMN id RESTART WITH 3;
