package com.mifichafavorita.gestionusuarios.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mifichafavorita.gestionusuarios.dto.CatalogoRequestDTO;
import com.mifichafavorita.gestionusuarios.dto.CatalogoResponseDTO;
import com.mifichafavorita.gestionusuarios.enums.EstadoVentaEnum;
import com.mifichafavorita.gestionusuarios.service.CatalogoService;

import lombok.RequiredArgsConstructor;
//
@RestController
@RequestMapping("/catalogo")
@RequiredArgsConstructor
public class CatalogoController {
    private final CatalogoService catalogoService;

    /**
     * Lista todos los productos
     * @return 
     */
    @GetMapping("/listar")
    public ResponseEntity<List<CatalogoResponseDTO>> listarProductos() {
        try {
            List<CatalogoResponseDTO> response = catalogoService.listarProductos();
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Lista productos por estado
     * @param estado
     * @return 
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CatalogoResponseDTO>> listarProductosPorEstado(@PathVariable EstadoVentaEnum estado) {
        try {
            return ResponseEntity.ok(catalogoService.listarPorEstado(estado));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Crear nuevo producto (solo ADMIN)
     * @param requestDTO
     * @param usuarioId
     * @return
     */
    @PostMapping
    public ResponseEntity<CatalogoResponseDTO> crearProducto(@RequestBody CatalogoRequestDTO requestDTO, @RequestParam Long usuarioId) {
        try {
            CatalogoResponseDTO response = catalogoService.crearProducto(requestDTO, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Confirmar venta (solo ADMIN y CAJERO)
     * @param productoId
     * @param usuarioId
     * @return
     */
    @PostMapping("/confirmar/{productoId}")
    public ResponseEntity<CatalogoResponseDTO> confirmarVenta(@PathVariable Long productoId, @RequestParam Long usuarioId) {
        try {
            CatalogoResponseDTO response = catalogoService.confirmarVenta(productoId, usuarioId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Comprar producto (solo CLIENTE)
     * @param productoId
     * @param cantidad
     * @param usuarioId
     * @return
     */
    @PostMapping("/comprar/{productoId}")
    public ResponseEntity<CatalogoResponseDTO> comprarProducto(@PathVariable Long productoId, @RequestParam Integer cantidad, @RequestParam Long usuarioId) {
        try {
            CatalogoResponseDTO response = catalogoService.comprarProducto(productoId, cantidad, usuarioId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Editar producto (solo ADMIN)
     * @param id
     * @param requestDTO
     * @param usuarioId
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CatalogoResponseDTO> editarProducto(@PathVariable Long id, @RequestBody CatalogoRequestDTO requestDTO, @RequestParam Long usuarioId) {
        try {
            CatalogoResponseDTO response = catalogoService.editarProducto(id, requestDTO, usuarioId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Eliminar producto (soft delete) (solo ADMIN)
     * @param id ID del producto a eliminar
     * @param usuarioId ID del usuario que elimina (debe ser ADMIN)
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id, @RequestParam Long usuarioId) {
        try {
            catalogoService.eliminarProducto(id, usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Cancelar venta pendiente (solo ADMIN o CAJERO)
     * @param productoId ID del producto a cancelar
     * @param usuarioId ID del usuario que cancela (debe ser ADMIN o CAJERO)
     * @return Producto actualizado
     */
    @PostMapping("/cancelar/{productoId}")
    public ResponseEntity<CatalogoResponseDTO> cancelarVenta(@PathVariable Long productoId, @RequestParam Long usuarioId) {
        try {
            CatalogoResponseDTO response = catalogoService.cancelarVenta(productoId, usuarioId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
