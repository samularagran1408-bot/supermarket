package com.mifichafavorita.gestionusuarios.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mifichafavorita.gestionusuarios.enums.EstadoVentaEnum;

import lombok.Data;

@Entity
@Data
@Table(name = "catalogo_productos")
public class Catalogo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "codigo_barras", nullable = false)
    private String codigoBarras;

    /**
     * Relaciones con usuarios
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por", nullable = false)
    private Users creadoPor; /* Id del Admin que creó el producto */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendido_por")
    private Users vendidoPor;  /* Id del Cajero/Admin que confirmó venta */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprado_por")
    private Users compradoPor;  /* Id del Cliente que compró */ 

    /**
     * Campos de Fecha
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    /**
     * Campos de Estado
     */
    @Column(name = "cantidad_comprada")
    private Integer cantidadComprada;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_venta")
    private EstadoVentaEnum estadoVenta;  /* "disponible", "pendiente", "vendido" */ 
    
    @Column(name = "activo")
    private Boolean activo;
}
