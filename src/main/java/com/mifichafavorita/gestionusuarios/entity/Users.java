package com.mifichafavorita.gestionusuarios.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/** Representa un usuario en la tabla {@code usuarios}. */
@Entity
@Data
@Table(name = "usuarios")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    /** Identificador del usuario. */
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    /** Nombre completo. */
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    /** Correo electrónico (único en la aplicación). */
    private String email;

    @Column(name = "password_hash ", nullable = false, length = 255)
    /** Contraseña ya hasheada. */
    private String password;

    @Column(name = "fecha_registro", nullable = false)
    /** Momento en que se registró el usuario. */
    private LocalDateTime fechaRegistro;

    @Column(name = "activo", nullable = false)
    /** Si la cuenta está activa o no. */
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id")
    /** Rol asignado al usuario. */
    private Rol rol;
    
}