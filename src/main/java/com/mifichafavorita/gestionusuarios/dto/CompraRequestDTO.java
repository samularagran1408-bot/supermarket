package com.mifichafavorita.gestionusuarios.dto;

import lombok.Data;

@Data
public class CompraRequestDTO {

    /**
     * Id del producto
     */
    private Long productoId;

    /**
     * Cantidad a comprar
     */
    private Integer cantidad;
}
