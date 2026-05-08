package com.mifichafavorita.gestionusuarios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mifichafavorita.gestionusuarios.dto.CatalogoRequestDTO;
import com.mifichafavorita.gestionusuarios.dto.CatalogoResponseDTO;
import com.mifichafavorita.gestionusuarios.dto.VentaConfirmDTO;
import com.mifichafavorita.gestionusuarios.entity.Catalogo;
import com.mifichafavorita.gestionusuarios.entity.Users;
import com.mifichafavorita.gestionusuarios.enums.EstadoVentaEnum;
import com.mifichafavorita.gestionusuarios.repository.CatalogoRepository;
import com.mifichafavorita.gestionusuarios.repository.UserRepository;

@Service
public class CatalogoService {
    
    private CatalogoRepository catalogoRepository;

    private UserRepository userRepository;

    /**
     * 
     * @param request
     * @param adminId
     * @return producto creado
     */
    @Transactional
    public CatalogoResponseDTO crearProducto(CatalogoRequestDTO request, Long adminId) {
        /**
         * Verificar que el admin existe
         */
        Users admin = userRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

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
        producto.setCreadoPor(admin);
        producto.setActivo(true);
        producto.setEstadoVenta(EstadoVentaEnum.DISPONIBLE);
        producto.setCantidadComprada(0);
        producto.setFechaCreacion(LocalDateTime.now());

        Catalogo guardado = catalogoRepository.save(producto);
        return convertirAResponseDTO(guardado);
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
