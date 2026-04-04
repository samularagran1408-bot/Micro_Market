-- Tablas para el módulo de empleados alineadas al modelo `usuario` de InkluSport.
-- Base de datos del proyecto: micro_market (ver application.yaml).
-- Si ya existía una tabla `employees` antigua (full_name, status boolean), elimínela o migre los datos antes de ejecutar esto.

-- ============================================
-- EMPLEADOS (equivalente operativo a usuario)
-- ============================================
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_number VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME,
    estado ENUM('activo','inactivo','bloqueado') NOT NULL DEFAULT 'activo',
    foto_perfil VARCHAR(255),
    token_recuperacion VARCHAR(255),
    token_expiracion DATETIME,
    position VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL
);

-- ============================================
-- ROL + EMPLEADO_ROL (misma idea que rol / usuario_rol en InkluSport)
-- ============================================
CREATE TABLE IF NOT EXISTS rol (
    id_rol INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS empleado_rol (
    id_empleado BIGINT NOT NULL,
    id_rol INT NOT NULL,
    fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_empleado, id_rol),
    CONSTRAINT fk_empleado_rol_empleado FOREIGN KEY (id_empleado) REFERENCES employees (id) ON DELETE CASCADE,
    CONSTRAINT fk_empleado_rol_rol FOREIGN KEY (id_rol) REFERENCES rol (id_rol) ON DELETE CASCADE
);
