-- ============================================
-- SCRIPT SQL PARA BASE DE DATOS LEARNCOMPANY
-- ============================================

-- Crear base de datos

CREATE DATABASE IF NOT EXISTS learncompany;
USE learncompany;

-- configurar charset
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================
-- TABLAS
-- ============================================

-- Tabla de departments

CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    prize VARCHAR(255) NOT NULL,
    hierarchy INT NOT NULL,
    -- Índices para búsquedas rápidas
    INDEX idx_name (name),             -- Buscar depto por nombre
    INDEX idx_hierarchy (hierarchy)    -- Consultas por jerarquía
);

-- Tabla de seasons

CREATE TABLE seasons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    duration_in_hours INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    INDEX idx_name (name)              -- Buscar temporadas por nombre
);

-- Tabla de badges

CREATE TABLE badges (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    url_icon VARCHAR(500) NOT NULL,
    criteria TEXT NOT NULL,
    INDEX idx_name (name)              -- Buscar badges por nombre
);

-- Tabla de users

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    role ENUM('EMPLOYEE', 'ADMINISTRATOR', 'INSTRUCTOR') NOT NULL,
    department_id BIGINT,
    url_photo VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL ON UPDATE CASCADE,

    INDEX idx_email (email),             -- Login
    INDEX idx_role (role),               -- Listar por rol
    INDEX idx_status (status),           -- Filtrar activos/inactivos
    INDEX idx_department_id (department_id), -- Filtrar por depto
    INDEX idx_user_department_role (department_id, role, status) -- Consultas compuestas (dashboard)
);
-- Tabla de administrators

CREATE TABLE administrators (
    id BIGINT PRIMARY KEY,
    age INT NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de employees

CREATE TABLE employees (
    id BIGINT PRIMARY KEY,
    puntos INT NOT NULL DEFAULT 0,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_puntos (puntos) -- Ranking rápido por puntos
);

-- Tabla de Instructors

CREATE TABLE instructors (
    id BIGINT PRIMARY KEY,
    specialty VARCHAR(200) NOT NULL,
    biography TEXT NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla courses


CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    topic VARCHAR(200) NOT NULL,
    description_course TEXT NOT NULL,
    level_course INT NOT NULL,
    duration_in_hours INT NOT NULL,
    season_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
    type_course ENUM('OPTIONAL', 'MANDATORY') NOT NULL,
    department_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (season_id) REFERENCES seasons(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT ON UPDATE CASCADE,

    INDEX idx_title (title), 
    INDEX idx_level (level_course),
    INDEX idx_season_id (season_id),
    INDEX idx_instructor_id (instructor_id),
    INDEX idx_department_id (department_id)
);

-- Tabla modules

CREATE TABLE modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_course_id (course_id)
);

-- Tabla inscriptions (n:m entre employee y course)

CREATE TABLE inscriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    date_issued TIMESTAMP NOT NULL,
    status_inscription ENUM('ACCEPTED', 'IN_PROGRESS', 'REJECTED') NOT NULL,
    
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_employee_course (employee_id, course_id),

    INDEX idx_employee_id (employee_id),
    INDEX idx_course_id (course_id),
    INDEX idx_status (status_inscription),
    INDEX idx_date_issued (date_issued)
);
-- Tabla certificates

CREATE TABLE certificates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    date_issued TIMESTAMP NOT NULL,
    
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_employee_course_cert (employee_id, course_id),

    INDEX idx_employee_id (employee_id),
    INDEX idx_course_id (course_id),
    INDEX idx_date_issued (date_issued)
);
-- ======================================================
-- EVALUACIONES, PREGUNTAS, RESPUESTAS
-- ======================================================

CREATE TABLE assessment_templates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_id BIGINT NOT NULL,
    type ENUM('QUIZ', 'WORKSHOP', 'FINAL_ASSESSMENT') NOT NULL,
    retries INT NOT NULL DEFAULT 3,
    
    FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_module_id (module_id),
    INDEX idx_type (type)
);

CREATE TABLE assessment_instances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assessment_template_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    grade DOUBLE NOT NULL DEFAULT 0.0,
    status_instance ENUM('PENDING', 'GRADED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (assessment_template_id) REFERENCES assessment_templates(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,

    INDEX idx_assessment_template_id (assessment_template_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_status (status_instance),
    INDEX idx_created_at (created_at),
    INDEX idx_assessment_employee_status (employee_id, status_instance) -- Evaluaciones por empleado y estado
);


CREATE TABLE questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assessment_template_id BIGINT NOT NULL,
    question TEXT NOT NULL,
    response_options TEXT NOT NULL,
    correct_answer VARCHAR(500) NOT NULL,
    question_order INT NOT NULL DEFAULT 1,
    
    FOREIGN KEY (assessment_template_id) REFERENCES assessment_templates(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_assessment_template_id (assessment_template_id),
    INDEX idx_question_order (question_order)
);

CREATE TABLE answers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assessment_instance_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    content_answer TEXT NOT NULL,
    date_issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (assessment_instance_id) REFERENCES assessment_instances(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_instance_question (assessment_instance_id, question_id), -- Evita respuestas duplicadas

    INDEX idx_assessment_instance_id (assessment_instance_id),
    INDEX idx_question_id (question_id),
    INDEX idx_date_issued (date_issued)
);
-- ======================================================
-- NOTIFICACIONES
-- ======================================================

CREATE TABLE notifications_content (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type_notification ENUM('NEW_EVALUATION', 'RANKING_WINNER') NOT NULL,
    reference_id BIGINT NOT NULL,
    message_notification TEXT,
    -- Índices para filtrar notificaciones
    INDEX idx_type (type_notification),
    INDEX idx_reference_id (reference_id)
);

CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    notification_content_id BIGINT NOT NULL,
    date_issued TIMESTAMP NOT NULL,
    read_status BOOLEAN NOT NULL DEFAULT FALSE,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (notification_content_id) REFERENCES notifications_content(id) ON DELETE CASCADE ON UPDATE CASCADE,


    INDEX idx_user_id (user_id),      
    INDEX idx_date_issued (date_issued), -- Notificaciones por fecha
    INDEX idx_read (read_status)                -- Filtrar leídas/no leídas
);

-- ======================================================
-- RELACIÓN MUCHOS A MUCHOS: EMPLOYEE - BADGE
-- ======================================================


CREATE TABLE employee_badges (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    badge_id BIGINT NOT NULL,
    date_earned TIMESTAMP NOT NULL,
    
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (badge_id) REFERENCES badges(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_employee_badge (employee_id, badge_id), -- Evitar duplicados

    INDEX idx_employee_id (employee_id),
    INDEX idx_badge_id (badge_id),
    INDEX idx_date_earned (date_earned)
);

-- ======================================================
-- ESTADÍSTICAS
-- ======================================================

CREATE TABLE statistics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY
    -- agregar columnas necesarias más adelante
);

-- ============================================
-- CONFIGURACIONES FINALES
-- ============================================

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- COMENTARIOS EN TABLAS
-- ============================================

ALTER TABLE users COMMENT = 'Tabla principal de usuarios con herencia (empleados, administradores, instructores)';
ALTER TABLE administrators COMMENT = 'Administradores que gestionan la plataforma';
ALTER TABLE employees COMMENT = 'Empleados que pueden inscribirse en cursos, acumular puntos y recibir badges';
ALTER TABLE instructors COMMENT = 'Instructores que dictan cursos';
ALTER TABLE departments COMMENT = 'Departamentos de la empresa con jerarquía y premios';
ALTER TABLE badges COMMENT = 'Insignias que pueden ganar los empleados según logros';
ALTER TABLE employee_badges COMMENT = 'Relación n:m entre empleados y badges obtenidos';
ALTER TABLE notifications COMMENT = 'Notificaciones emitidas a los usuarios';
ALTER TABLE notifications_content COMMENT = 'Plantillas de contenido de notificaciones';
ALTER TABLE courses COMMENT = 'Cursos disponibles en la plataforma, dictados por instructores y asociados a una temporada';
ALTER TABLE seasons COMMENT = 'Temporadas académicas que agrupan cursos';
ALTER TABLE inscriptions COMMENT = 'Inscripciones de empleados a cursos (estado: aceptado, en progreso o rechazado)';
ALTER TABLE certificates COMMENT = 'Certificados emitidos a empleados tras completar cursos';
ALTER TABLE modules COMMENT = 'Módulos que componen un curso';
ALTER TABLE assessment_templates COMMENT = 'Plantillas de evaluaciones asociadas a un módulo';
ALTER TABLE questions COMMENT = 'Preguntas de cada evaluación con sus opciones y respuesta correcta';
ALTER TABLE assessment_instances COMMENT = 'Instancias de evaluaciones tomadas por empleados';
ALTER TABLE answers COMMENT = 'Respuestas de empleados a las preguntas de una evaluación';
ALTER TABLE statistics COMMENT = 'Tabla para almacenar estadísticas de uso y rendimiento';
-- ============================================

-- ============================================
-- VISTAS PARA CONSULTAS COMUNES
-- ============================================

-- Vista de usuarios con su departamento y rol
CREATE OR REPLACE VIEW v_users_details AS
SELECT 
    u.id,
    u.name,
    u.lastname,
    u.email,
    u.role,
    d.name AS department,
    u.status,
    u.created_at
FROM users u
LEFT JOIN departments d ON u.department_id = d.id;

-- Vista de inscripciones con detalle de curso y empleado
CREATE OR REPLACE VIEW v_inscriptions_detail AS
SELECT 
    i.id AS inscription_id,
    e.id AS employee_id,
    CONCAT(u.name, ' ', u.lastname) AS employee_name,
    c.title AS course_title,
    i.status_inscription,
    i.date_issued
FROM inscriptions i
INNER JOIN employees e ON i.employee_id = e.id
INNER JOIN users u ON e.id = u.id
INNER JOIN courses c ON i.course_id = c.id;

-- Vista de certificados con detalle de curso y empleado
CREATE OR REPLACE VIEW v_certificates_detail AS
SELECT 
    cert.id AS certificate_id,
    e.id AS employee_id,
    CONCAT(u.name, ' ', u.lastname) AS employee_name,
    c.title AS course_title,
    cert.date_issued
FROM certificates cert
INNER JOIN employees e ON cert.employee_id = e.id
INNER JOIN users u ON e.id = u.id
INNER JOIN courses c ON cert.course_id = c.id;

-- Vista de evaluaciones con nota y estado
CREATE OR REPLACE VIEW v_assessments_summary AS
SELECT 
    ai.id AS assessment_instance_id,
    e.id AS employee_id,
    CONCAT(u.name, ' ', u.lastname) AS employee_name,
    m.title AS module_title,
    c.title AS course_title,
    ai.grade,
    ai.status_instance,
    ai.created_at
FROM assessment_instances ai
INNER JOIN employees e ON ai.employee_id = e.id
INNER JOIN users u ON e.id = u.id
INNER JOIN assessment_templates atpl ON ai.assessment_template_id = atpl.id
INNER JOIN modules m ON atpl.module_id = m.id
INNER JOIN courses c ON m.course_id = c.id;
-- ============================================

COMMIT;

-- DROP DATABASE learncompany;