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

    /**
     * Se asigna un valor a esta constante id
     * @param id
     */

    RolEnum(Long id) {
        this.id = id;
    }

    /**
     * Retorna el valor de la constante
     * @return
     */

    public Long getId() {
        return id;
    }
}