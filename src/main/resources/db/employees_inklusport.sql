-- Tabla employees (micro_market). Sin email ni foto de perfil.

CREATE TABLE IF NOT EXISTS employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_number VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    position ENUM('ADMINISTRATOR', 'CASHIER', 'ASSISTANT') NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    status BOOLEAN DEFAULT TRUE
);
