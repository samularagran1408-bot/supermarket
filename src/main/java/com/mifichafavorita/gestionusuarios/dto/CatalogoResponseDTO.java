package com.mifichafavorita.gestionusuarios.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CatalogoResponseDTO {

    /**
     * Id del producto
     */
    private Long id;

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
    private Integer stock;

    /**
     * Codigo de Barras del producto
     */
    private String codigoBarras;

    /**
     * Estado de venta del producto
     */
    private String estadoVenta;

    /**
     * Indica si el producto está activo
     */
    private Boolean activo;

    /**
     * Fecha de creación del producto
     */
    private LocalDateTime fechaCreacion;

    /**
     * Fecha de venta del producto
     */
    private LocalDateTime fechaVenta;

    /**
     * Cantidad comprada del producto
     */
    private Integer cantidadComprada;
    
    // Información de los usuarios (opcional)
    private String nombreCreador;
    private String nombreComprador;
    private String nombreVendedor;
}