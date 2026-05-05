package com.mifichafavorita.gestionusuarios.enums;

public enum RolEnum {
    /** Enums for Roles */
    /**
     * Ya se puede trabajar con develop
     */
    ADMIN(1L),
    CLIENTE(2L), 
    CAJERO(3L);

    private final Long id;

    RolEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}