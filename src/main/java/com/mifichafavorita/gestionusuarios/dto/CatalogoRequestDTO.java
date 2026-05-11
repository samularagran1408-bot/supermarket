package com.mifichafavorita.gestionusuarios.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CatalogoRequestDTO {
    
    /**
     * Nombre del producto
     */
    private String nombre;

    /**
     * Descripción del producto
     */
    private String descripcion;

    /**
     * Precio del producto
     */
    private BigDecimal precio;

    /**
     * Stock del producto
     */
    private Long stock;

    /**
     * Codigo de Barras del producto
     */
    private String codigoBarras;
}
