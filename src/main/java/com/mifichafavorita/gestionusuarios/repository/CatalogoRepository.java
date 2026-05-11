package com.mifichafavorita.gestionusuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mifichafavorita.gestionusuarios.entity.Catalogo;
import com.mifichafavorita.gestionusuarios.enums.EstadoVentaEnum;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
    /**
     * Buscar producto por Código de Barras
     * @param codigoBarras
     * @return Productos
     */
    Optional<Catalogo> findByCodigoBarras(String codigoBarras);

    /**
     * Buscar por nombre (ignorando mayúsculas/minúsculas)
     * @param nombre
     * @return Productos
     */
    List<Catalogo> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Productos Activos
     * @return Productos Activos
     */
    List<Catalogo> findByActivoTrue();

    /**
     * 
     * @param estadoVenta
     * @return Productos Encontrados por su Estados
     */
    List<Catalogo> findByEstadoVenta(EstadoVentaEnum estadoVenta);

    /**
     * Busca Productos por Admin
     * @param creadoPor
     * @return Productos creados por un admin específico
     */
    List<Catalogo> findByCreadoPor(Integer creadoPor);
    
    /**
     * Busca Productos vendidos por un cajero/admin específico
     * @param vendidoPor
     * @return Productos vendidos por un cajero/admin específico
     */
    List<Catalogo> findByVendidoPor(Integer vendidoPor);
    
    /**
     * Busca Productos comprados cliente
     * @param compradoPor
     * @return Productos comprados por un cliente específico
     */
    List<Catalogo> findByCompradoPor(Integer compradoPor);

    /**
     * Existe por codigo de Barras
     * @param codigoBarras
     * @return Producto con su Codigo de Barras
     */
    boolean existsByCodigoBarras(String codigoBarras);
}
