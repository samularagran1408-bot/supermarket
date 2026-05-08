package com.mifichafavorita.gestionusuarios.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mifichafavorita.gestionusuarios.dto.CatalogoRequestDTO;
import com.mifichafavorita.gestionusuarios.dto.CatalogoResponseDTO;
import com.mifichafavorita.gestionusuarios.entity.Catalogo;
import com.mifichafavorita.gestionusuarios.entity.Rol.RolName;
import com.mifichafavorita.gestionusuarios.entity.Users;
import com.mifichafavorita.gestionusuarios.enums.EstadoVentaEnum;
import com.mifichafavorita.gestionusuarios.repository.CatalogoRepository;
import com.mifichafavorita.gestionusuarios.repository.UserRepository;

@Service
public class CatalogoService {
    
    private CatalogoRepository catalogoRepository;

    private UserRepository userRepository;

    /**
     * Lista todos los productos
     * @return Lista de productos
     */
    public List<CatalogoResponseDTO> listarProductos() {
        List<Catalogo> productosFound = catalogoRepository.findByActivoTrue();
        List<CatalogoResponseDTO> response = new ArrayList<>();
        for (Catalogo productos : productosFound) {
            CatalogoResponseDTO producto = new CatalogoResponseDTO();
            producto.setId(productos.getId());
            producto.setNombre(productos.getNombre());
            producto.setDescripcion(productos.getDescripcion());
            producto.setPrecio(productos.getPrecio());
            producto.setStock(productos.getStock());
            producto.setCodigoBarras(productos.getCodigoBarras());
            producto.setFechaCreacion(productos.getFechaCreacion());
            producto.setFechaVenta(productos.getFechaVenta());
            producto.setCantidadComprada(productos.getCantidadComprada());
            producto.setActivo(productos.getActivo());
            
            if (producto.getEstadoVenta() != null) {
                producto.setEstadoVenta(producto.getEstadoVenta());
            }
            response.add(producto);
        }
        return response;
    }

    public List<CatalogoResponseDTO> listarPorEstado(EstadoVentaEnum estado) {
        List<Catalogo> productosFound = catalogoRepository.findByActivoTrue();
        List<CatalogoResponseDTO> response = new ArrayList<>();
        for (Catalogo productos : productosFound) {
            CatalogoResponseDTO producto = new CatalogoResponseDTO();
            producto.setId(productos.getId());
            producto.setNombre(productos.getNombre());
            producto.setDescripcion(productos.getDescripcion());
            producto.setPrecio(productos.getPrecio());
            producto.setStock(productos.getStock());
            producto.setCodigoBarras(productos.getCodigoBarras());
            producto.setFechaCreacion(productos.getFechaCreacion());
            producto.setFechaVenta(productos.getFechaVenta());
            producto.setCantidadComprada(productos.getCantidadComprada());
            producto.setActivo(productos.getActivo());
            
            if (producto.getEstadoVenta() != null) {
                producto.setEstadoVenta(producto.getEstadoVenta());
            }
            response.add(producto);
        }
        return response;
    }

    /**
     * 
     * @param request
     * @param usuarioId
     * @return producto creado
     */
    @Transactional
    public CatalogoResponseDTO crearProducto(CatalogoRequestDTO request, Long usuarioId) {
        /**
         * Verificar que el usuario existe
         */
        Users usuario = userRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        /**
         * Verificar que el usuario tenga el rol de administrador
         */
        RolName rol = usuario.getRol().getName();
        if (rol != RolName.ADMIN) {
            throw new RuntimeException("ACCESO DENEGADO: Solo los administradores pueden agregar productos. Tu rol es: " + rol);
        }
        
        /**
         * Verificar si ya existe un producto con ese código de barras
         */
        if (catalogoRepository.existsByCodigoBarras(request.getCodigoBarras())) {
            throw new RuntimeException("Ya existe un producto con ese código de barras");
        }

        Catalogo producto = new Catalogo();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCodigoBarras(request.getCodigoBarras());
        producto.setCreadoPor(usuario);
        producto.setActivo(true);
        producto.setEstadoVenta(EstadoVentaEnum.DISPONIBLE);
        producto.setCantidadComprada(0);
        producto.setFechaCreacion(LocalDateTime.now());

        Catalogo guardado = catalogoRepository.save(producto);
        return convertirAResponseDTO(guardado);
    }

    /**
     * Confirmar venta del producto
     * @param productoId
     * @param usuarioId
     * @return producto actualizado
     */
    @Transactional
    public CatalogoResponseDTO confirmarVenta(Long productoId, Long usuarioId) {
        /**
         * Verificar que el producto existe
         */
        Catalogo producto = catalogoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        /**
         * Verificar que el usuario existe
         */
        Users usuario = userRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        /**
         * Verificar que el usuario tenga el rol de cliente
         */
        RolName rol = usuario.getRol().getName();
        if (rol == RolName.CLIENTE) {
            throw new RuntimeException("ACCESO DENEGADO: Solo los administradores y cajeros pueden confirmar ventas. Tu rol es: " + rol);
        }

        /**
         * Confirmar venta
         */
        producto.setVendidoPor(usuario);
        producto.setEstadoVenta(EstadoVentaEnum.VENDIDO);

        Catalogo actualizado = catalogoRepository.save(producto);
        return convertirAResponseDTO(actualizado);
    }
    
    /**
     * Comprar producto
     * @param productoId
     * @param cantidad
     * @param usuarioId
     * @return producto actualizado
     */
    @Transactional
    public CatalogoResponseDTO comprarProducto(Long productoId, Integer cantidad, Long usuarioId) {
        
        /**
         * Verificar que el producto existe
         */
        Catalogo producto = catalogoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        /**
         * Verificar que el usuario existe
         */
        Users usuario = userRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        /**
         * Confirmar compra
         */
        producto.setStock(producto.getStock() - cantidad);
        producto.setCompradoPor(usuario);
        producto.setCantidadComprada(cantidad);
        producto.setEstadoVenta(EstadoVentaEnum.COMPRADO);
        producto.setFechaVenta(LocalDateTime.now());

        Catalogo actualizado = catalogoRepository.save(producto);
        return convertirAResponseDTO(actualizado);
    }

    /**
     * Editar producto - solo admin
     * @param id
     * @param request
     * @param usuarioId
     * @return producto actualizado
     */
    @Transactional
    public CatalogoResponseDTO editarProducto(Long id, CatalogoRequestDTO request, Long usuarioId) {

        /**
         * Verificar que el producto existe
         */
        Users usuario = userRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        /**
         * Verificar que el usuario tenga el rol de administrador
         */
        RolName rol = usuario.getRol().getName();
        if (rol != RolName.ADMIN) {
            throw new RuntimeException("ACCESO DENEGADO: Solo los administradores pueden editar productos. Tu rol es: " + rol);
        }

        /**
         * Verificar que el producto existe
         */
        Catalogo producto = catalogoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        /**
         * Validar código de barras si cambió
         */
        if(request.getCodigoBarras() != null && !request.getCodigoBarras().equals(producto.getCodigoBarras())) {
            if (catalogoRepository.existsByCodigoBarras(request.getCodigoBarras())) {
                throw new RuntimeException("Ya existe otro producto con ese código de barras");
            }
            producto.setCodigoBarras(request.getCodigoBarras());
        }

        /**
         * Actualizar producto
         */
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());

        Catalogo actualizado = catalogoRepository.save(producto);
        return convertirAResponseDTO(actualizado);
    }

    /**
     * Eliminar producto - solo admin
     * @param id
     * @param usuarioId
     * @return producto actualizado
     */
    @Transactional
    public void eliminarProducto(Long id, Long usuarioId) {
        /**
         * Verificar que el producto existe
         */
        Catalogo producto = catalogoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        /**
         * Verificar que el usuario existe
         */
        Users usuario = userRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        /**
         * Verificar que el usuario tenga el rol de administrador
         */
        RolName rol = usuario.getRol().getName();
        if (rol != RolName.ADMIN) {
            throw new RuntimeException("ACCESO DENEGADO: Solo los administradores pueden eliminar productos. Tu rol es: " + rol);
        }

        /**
         * Soft delete
         */
        producto.setActivo(false);
        catalogoRepository.save(producto);
    }

    /**
     * Cancelar compra - solo admin y cajero
     * @param id
     * @param usuarioId
     * @return producto actualizado
     */
    @Transactional
    public CatalogoResponseDTO cancelarVenta(Long productoId, Long usuarioId) {
        /**
         * Verificar que el producto existe
         */
        Catalogo producto = catalogoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        /**
         * Verificar que el usuario existe
         */
        Users usuario = userRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        /**
         * Verificar que el usuario tenga el rol diferente a cliente
         */
        RolName rol = usuario.getRol().getName();
        if (rol != RolName.CAJERO && rol != RolName.ADMIN) {
            throw new RuntimeException("ACCESO DENEGADO: Solo los administradores y cajeros pueden cancelar ventas. Tu rol es: " + rol);
        }

        /**
         * Cancelar venta
         */
        producto.setStock(producto.getStock() + producto.getCantidadComprada());
        producto.setCompradoPor(null);
        producto.setCantidadComprada(0);
        producto.setEstadoVenta(EstadoVentaEnum.DISPONIBLE);
        producto.setVendidoPor(null);
        producto.setFechaVenta(null);
        
        Catalogo actualizado = catalogoRepository.save(producto);
        return convertirAResponseDTO(actualizado);
    }

    /**
     * Convierte todo a response
     * @param producto
     * @return dto
     */
    private CatalogoResponseDTO convertirAResponseDTO(Catalogo producto) {
        CatalogoResponseDTO dto = new CatalogoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setFechaVenta(producto.getFechaVenta());
        dto.setCantidadComprada(producto.getCantidadComprada());
        dto.setActivo(producto.getActivo());
        
        if (producto.getEstadoVenta() != null) {
            dto.setEstadoVenta(producto.getEstadoVenta().name());
        }
        
        // Información de usuarios (opcional)
        if (producto.getCreadoPor() != null) {
            dto.setNombreCreador(producto.getCreadoPor().getName());
        }
        if (producto.getCompradoPor() != null) {
            dto.setNombreComprador(producto.getCompradoPor().getName());
        }
        if (producto.getVendidoPor() != null) {
            dto.setNombreVendedor(producto.getVendidoPor().getName());
        }
        
        return dto;
    }
}
