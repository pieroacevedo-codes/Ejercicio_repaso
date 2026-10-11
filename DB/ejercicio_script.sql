CREATE SCHEMA IF NOT EXISTS ejercicio;

CREATE TABLE ejercicio.tm_persona (
    id_persona     BIGSERIAL PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    ap_paterno     VARCHAR(100) NOT NULL,
    ap_materno     VARCHAR(100) NOT NULL,
    dni            VARCHAR(8)   NOT NULL UNIQUE,
    estado_persona INTEGER NOT NULL
);

CREATE TABLE ejercicio.tm_curso (
    id_curso     BIGSERIAL PRIMARY KEY,
    curso        VARCHAR(100) NOT NULL,
    codigo       VARCHAR(20)  NOT NULL UNIQUE,
    estado_curso INTEGER NOT NULL
);

-- Tabla intermedia: solo guarda los id de persona y curso
CREATE TABLE ejercicio.tm_persona_curso (
    id_persona_curso     BIGSERIAL PRIMARY KEY,
    id_persona           BIGINT NOT NULL,
    id_curso             BIGINT NOT NULL,
    estado_persona_curso INTEGER NOT NULL,
    CONSTRAINT fk_pc_persona FOREIGN KEY (id_persona) REFERENCES ejercicio.tm_persona (id_persona),
    CONSTRAINT fk_pc_curso   FOREIGN KEY (id_curso)   REFERENCES ejercicio.tm_curso (id_curso),
    -- Una persona no puede estar matriculada dos veces en el mismo curso
    CONSTRAINT uq_pc_persona_curso UNIQUE (id_persona, id_curso)
);

-- Una fila por registro de cualquier tabla
CREATE TABLE ejercicio.tm_auditoria (
    id_auditoria      BIGSERIAL PRIMARY KEY,
    tabla_afectada    VARCHAR(50) NOT NULL,
    id_registro       BIGINT NOT NULL,
    fecha_creacion    TIMESTAMP NOT NULL,
    fecha_edicion     TIMESTAMP,
    fecha_eliminacion TIMESTAMP,
    estado_auditoria  INTEGER NOT NULL,
    CONSTRAINT uq_auditoria_tabla_registro UNIQUE (tabla_afectada, id_registro)
);

-- ===== Datos del ejercicio =====
INSERT INTO ejercicio.tm_persona (nombre, ap_paterno, ap_materno, dni, estado_persona)
VALUES ('Stevens Adolfo', 'Conde', 'Meza', '12345678', 1);          -- id 1
INSERT INTO ejercicio.tm_persona (nombre, ap_paterno, ap_materno, dni, estado_persona)
VALUES ('Carlos', 'Perez', 'Lopez', '23456789', 1);                 -- id 2
INSERT INTO ejercicio.tm_persona (nombre, ap_paterno, ap_materno, dni, estado_persona)
VALUES ('Gustavo', 'Ramos', 'Diaz', '34567890', 1);                 -- id 3

INSERT INTO ejercicio.tm_curso (curso, codigo, estado_curso) VALUES ('ARQ WEB', 'V2026', 1);  -- id 1
INSERT INTO ejercicio.tm_curso (curso, codigo, estado_curso) VALUES ('POO', 'P2026', 1);      -- id 2

-- persona 1 (Stevens) en el curso 2 (POO)
INSERT INTO ejercicio.tm_persona_curso (id_persona, id_curso, estado_persona_curso) VALUES (1, 2, 1);

INSERT INTO ejercicio.tm_auditoria (tabla_afectada, id_registro, fecha_creacion, estado_auditoria) VALUES
 ('tm_persona', 1, now(), 1), ('tm_persona', 2, now(), 1), ('tm_persona', 3, now(), 1),
 ('tm_curso', 1, now(), 1), ('tm_curso', 2, now(), 1),
 ('tm_persona_curso', 1, now(), 1);

-- ===== Listado de tm_persona_curso (con los nombres) =====
SELECT pc.id_persona_curso, pc.id_persona, p.nombre, p.ap_paterno, p.ap_materno,
       pc.id_curso, c.curso, c.codigo
FROM ejercicio.tm_persona_curso pc
JOIN ejercicio.tm_persona p ON p.id_persona = pc.id_persona
JOIN ejercicio.tm_curso   c ON c.id_curso   = pc.id_curso
WHERE pc.estado_persona_curso = 1
ORDER BY pc.id_persona_curso;
