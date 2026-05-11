CREATE DATABASE IF NOT EXISTS supermercado_db2;
USE supermercado_db2;

-- Tabla: Roles (independiente)
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre ENUM('cliente', 'cajero', 'admin') NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- Tabla: Usuarios (con FK hacia roles)
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol_id INT NOT NULL,  -- ← FK hacia roles
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE RESTRICT
);

-- Tabla: Catálogo de productos
CREATE TABLE catalogo_productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    codigo_barras VARCHAR(50) UNIQUE,
    
    creado_por INT NOT NULL,
    vendido_por INT,
    comprado_por INT,
    
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_venta TIMESTAMP NULL,
    cantidad_comprada INT DEFAULT 0,
    estado_venta ENUM('disponible', 'vendido', 'pendiente', 'comprado') DEFAULT 'disponible',
    activo BOOLEAN DEFAULT TRUE,
    
    FOREIGN KEY (creado_por) REFERENCES usuarios(id),
    FOREIGN KEY (vendido_por) REFERENCES usuarios(id),
    FOREIGN KEY (comprado_por) REFERENCES usuarios(id)
);

INSERT INTO roles (nombre, descripcion) VALUES 
('admin', 'Acceso total al sistema - Puede gestionar productos y usuarios'),
('cajero', 'Puede comprar, confirmar ventas y ver productos'),
('cliente', 'Solo puede comprar productos y ver el catálogo');

select * from roles;
select * from usuarios;
select * from catalogo_productos;
