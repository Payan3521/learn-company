-- ============================================
-- SCRIPT SQL PARA BASE DE DATOS LEARNCOMPANY
-- ============================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS learncompany;
USE learncompany;

-- Configurar charset
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================
-- TABLAS INDEPENDIENTES (Sin FK)
-- ============================================

-- Tabla departments
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    prize VARCHAR(255) NOT NULL,
    hierarchy INT NOT NULL,
    INDEX idx_name (name),
    INDEX idx_hierarchy (hierarchy)
);

-- Tabla seasons
CREATE TABLE seasons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    duration_in_hours INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    INDEX idx_name (name)
);

-- Tabla badges
CREATE TABLE badges (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    url_icon VARCHAR(500) NOT NULL,
    criteria TEXT NOT NULL,
    INDEX idx_name (name)
);

-- Tabla notifications_content
CREATE TABLE notifications_content (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type ENUM('NEW_EVALUATION', 'RANKING_WINNER') NOT NULL,
    reference_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    INDEX idx_type (type),
    INDEX idx_reference_id (reference_id)
);

-- ============================================
-- TABLA USERS Y HERENCIA
-- ============================================

-- Tabla users (padre)
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
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status),
    INDEX idx_department_id (department_id)
);

-- Tabla administrators (herencia)
CREATE TABLE administrators (
    id BIGINT PRIMARY KEY,
    age INT NOT NULL,
    
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla employees (herencia)
CREATE TABLE employees (
    id BIGINT PRIMARY KEY,
    puntos INT NOT NULL DEFAULT 0,
    
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_puntos (puntos)
);

-- Tabla instructors (herencia)
CREATE TABLE instructors (
    id BIGINT PRIMARY KEY,
    specialty VARCHAR(200) NOT NULL,
    biography TEXT NOT NULL,
    
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ============================================
-- TABLAS DEPENDIENTES DE USERS
-- ============================================

-- Tabla notifications
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    notification_content_id BIGINT NOT NULL,
    date_issued TIMESTAMP NOT NULL,
    read BOOLEAN NOT NULL DEFAULT FALSE,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (notification_content_id) REFERENCES notifications_content(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_date_issued (date_issued),
    INDEX idx_read (read)
);

-- Tabla courses
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    topic VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    level INT NOT NULL,
    duration_in_hours INT NOT NULL,
    season_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (season_id) REFERENCES seasons(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_title (title),
    INDEX idx_level (level),
    INDEX idx_season_id (season_id),
    INDEX idx_instructor_id (instructor_id)
);

-- ============================================
-- TABLAS DE RELACIONES n:m
-- ============================================

-- Tabla employee_badges (n:m entre employee y badge)
CREATE TABLE employee_badges (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    badge_id BIGINT NOT NULL,
    date_earned TIMESTAMP NOT NULL,
    
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (badge_id) REFERENCES badges(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_employee_badge (employee_id, badge_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_badge_id (badge_id),
    INDEX idx_date_earned (date_earned)
);

-- Tabla inscriptions (n:m entre employee y course)
CREATE TABLE inscriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    date_issued TIMESTAMP NOT NULL,
    status ENUM('Accepted', 'inProgress', 'rejected') NOT NULL,
    
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_employee_course (employee_id, course_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_course_id (course_id),
    INDEX idx_status (status),
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

-- ============================================
-- ESTRUCTURA DE CURSOS Y EVALUACIONES
-- ============================================

-- Tabla modules
CREATE TABLE modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    module_order INT NOT NULL DEFAULT 1,
    
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_course_id (course_id),
    INDEX idx_module_order (module_order)
);

-- Tabla assessment_templates
CREATE TABLE assessment_templates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_id BIGINT NOT NULL,
    type ENUM('QUIZ', 'WORKSHOP', 'FINAL_ASSESSMENT') NOT NULL,
    retries INT NOT NULL DEFAULT 3,
    
    FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_module_id (module_id),
    INDEX idx_type (type)
);

-- Tabla questions
CREATE TABLE questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assessment_template_id BIGINT NOT NULL,
    question TEXT NOT NULL,
    response_options JSON NOT NULL,
    correct_answer VARCHAR(500) NOT NULL,
    question_order INT NOT NULL DEFAULT 1,
    
    FOREIGN KEY (assessment_template_id) REFERENCES assessment_templates(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_assessment_template_id (assessment_template_id),
    INDEX idx_question_order (question_order)
);

-- Tabla assessment_instances
CREATE TABLE assessment_instances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assessment_template_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    grade DOUBLE NOT NULL DEFAULT 0.0,
    status ENUM('PENDING', 'GRADED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    submitted_at TIMESTAMP NULL,
    
    FOREIGN KEY (assessment_template_id) REFERENCES assessment_templates(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_assessment_template_id (assessment_template_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- Tabla answers
CREATE TABLE answers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assessment_instance_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    date_issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_correct BOOLEAN NULL,
    
    FOREIGN KEY (assessment_instance_id) REFERENCES assessment_instances(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_instance_question (assessment_instance_id, question_id),
    INDEX idx_assessment_instance_id (assessment_instance_id),
    INDEX idx_question_id (question_id),
    INDEX idx_date_issued (date_issued)
);

-- ============================================
-- DATOS INICIALES
-- ============================================

-- Insertar departamentos básicos
INSERT INTO departments (name, prize, hierarchy) VALUES
('Tecnología', 'Laptop Premium', 1),
('Recursos Humanos', 'Bono Vacacional', 2),
('Marketing', 'Kit de Herramientas Digitales', 2),
('Ventas', 'Comisión Extra', 1),
('Administración', 'Curso Especializado', 3);

-- Insertar temporadas
INSERT INTO seasons (duration_in_hours, name) VALUES
(120, 'Temporada Primavera 2024'),
(100, 'Temporada Verano 2024'),
(140, 'Temporada Otoño 2024'),
(80, 'Temporada Intensiva');

-- Insertar badges básicos
INSERT INTO badges (name, url_icon, criteria) VALUES
('Principiante', '/icons/beginner.png', 'Completar primer curso'),
('Estudioso', '/icons/studious.png', 'Completar 5 cursos'),
('Experto', '/icons/expert.png', 'Obtener promedio mayor a 90'),
('Mentor', '/icons/mentor.png', 'Ayudar a 10 compañeros'),
('Innovador', '/icons/innovator.png', 'Proponer mejora implementada');

-- Insertar contenidos de notificación básicos
INSERT INTO notifications_content (type, reference_id, message) VALUES
('NEW_EVALUATION', 1, 'Tienes una nueva evaluación disponible'),
('RANKING_WINNER', 1, '¡Felicidades! Has ganado el ranking mensual');

-- ============================================
-- CONFIGURACIONES FINALES
-- ============================================

SET FOREIGN_KEY_CHECKS = 1;

-- Crear usuario para la aplicación (opcional)
-- CREATE USER 'learncompany_user'@'localhost' IDENTIFIED BY 'secure_password';
-- GRANT ALL PRIVILEGES ON learncompany.* TO 'learncompany_user'@'localhost';
-- FLUSH PRIVILEGES;

-- ============================================
-- VISTAS ÚTILES (OPCIONAL)
-- ============================================

-- Vista para ver empleados con su información completa
CREATE VIEW employee_details AS
SELECT 
    e.id,
    u.name,
    u.lastname,
    u.email,
    e.puntos,
    d.name as department_name,
    u.status
FROM employees e
JOIN users u ON e.id = u.id
LEFT JOIN departments d ON u.department_id = d.id;

-- Vista para ver cursos con instructor
CREATE VIEW course_details AS
SELECT 
    c.id,
    c.title,
    c.topic,
    c.level,
    c.duration_in_hours,
    s.name as season_name,
    CONCAT(u.name, ' ', u.lastname) as instructor_name
FROM courses c
JOIN seasons s ON c.season_id = s.id
JOIN instructors i ON c.instructor_id = i.id
JOIN users u ON i.id = u.id;

-- ============================================
-- ÍNDICES ADICIONALES PARA PERFORMANCE
-- ============================================

-- Índices compuestos para consultas frecuentes
CREATE INDEX idx_user_department_role ON users (department_id, role, status);
CREATE INDEX idx_course_season_level ON courses (season_id, level);
CREATE INDEX idx_inscription_status_date ON inscriptions (status, date_issued);
CREATE INDEX idx_assessment_employee_status ON assessment_instances (employee_id, status);

-- ============================================
-- COMENTARIOS EN TABLAS
-- ============================================

ALTER TABLE users COMMENT = 'Tabla principal de usuarios con herencia';
ALTER TABLE employees COMMENT = 'Empleados que pueden tomar cursos';
ALTER TABLE courses COMMENT = 'Cursos disponibles en la plataforma';
ALTER TABLE employee_badges COMMENT = 'Relación n:m entre empleados y badges';
ALTER TABLE assessment_instances COMMENT = 'Instancias de evaluaciones tomadas por empleados';

COMMIT;