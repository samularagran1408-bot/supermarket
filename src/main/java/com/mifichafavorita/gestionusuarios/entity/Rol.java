package com.mifichafavorita.gestionusuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Rol")
public class Rol {

    /**
     * Genera automaticamente el id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El nombre del rol, que es único y no puede ser nulo
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Una descripción opcional del rol
     */

    @Column(name = "description", length = 255)
    private String description;

    /**
     * Enum para los nombres de los roles, que son cliente, cajero y admin
     */

    public enum RolName {
        CLIENTE, CAJERO, ADMIN
    }

}