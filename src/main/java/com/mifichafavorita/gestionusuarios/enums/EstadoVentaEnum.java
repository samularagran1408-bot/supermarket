package com.mifichafavorita.gestionusuarios.enums;

public enum EstadoVentaEnum {
    
    DISPONIBLE("disponible", "Producto disponible para compra"),
    PENDIENTE("pendiente", "Venta realizada, esperando confirmación"),
    VENDIDO("vendido", "Venta confirmada y completada");
    
    private final String codigo;
    private final String descripcion;
    
    EstadoVentaEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Convertir String a Enum (útil para peticiones)
     * @param codigo
     * @return estado
     */
    public static EstadoVentaEnum fromCodigo(String codigo) {
        for (EstadoVentaEnum estado : values()) {
            if (estado.codigo.equalsIgnoreCase(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado desconocido: " + codigo);
    }
    
    /**
     * Verificar si se puede comprar
     * @return this == DISPONIBLE
     */
    public boolean isDisponible() {
        return this == DISPONIBLE;
    }
    
    /**
     * Verificar si se puede confirmar
     * @return this == PENDIENTE
     */
    public boolean isPendiente() {
        return this == PENDIENTE;
    }
}