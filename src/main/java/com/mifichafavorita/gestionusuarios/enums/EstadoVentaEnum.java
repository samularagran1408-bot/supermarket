package com.mifichafavorita.gestionusuarios.enums;

public enum EstadoVentaEnum {
    
    disponible("disponible", "Producto disponible para compra"),
    pendiente("pendiente", "Venta realizada, esperando confirmación"),
    vendido("vendido", "Venta confirmada y completada"), 
    comprado("comprado", "Producto comprado");
    
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
     * @return this == disponible
     */
    public boolean isDisponible() {
        return this == disponible;
    }
    
    /**
     * Verificar si se puede confirmar
     * @return this == pendiente
     */
    public boolean isPendiente() {
        return this == pendiente;
    }
}