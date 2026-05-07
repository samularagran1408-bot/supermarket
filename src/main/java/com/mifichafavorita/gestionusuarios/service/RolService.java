package com.mifichafavorita.gestionusuarios.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mifichafavorita.gestionusuarios.entity.Rol;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    /**
     * Inyección del repositorio del rol
     */

    private final RolRepository rolRepository;

    /**
     * Listar todos los roles
     * 
     * @return
     */

    public List<RolDTO> listAll() {
        return rolRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca por id
     * 
     * @param id
     * @return
     */

    public Optional<RolDTO> getById(Integer id) {
        return rolRepository.findById(id)
                .map(this::convertirADTO);
    }

    /**
     * Busca por nombre del enum
     * 
     * @param name
     * @return
     */

    public Optional<Rol> getEntityByName(Rol.RolName name) {
        return rolRepository.findByName(name);
    }

    /**
     * Buscar por nombre como string
     * 
     * @param name
     * @return
     */

    public Optional<Rol> getByNameString(String name) {
        try {
            Rol.RolName rolName = Rol.RolName.valueOf(name.toLowerCase());
            return rolRepository.findByName(rolName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("invalid role: " + name + "The allowed roles are: CLIENTE, CAJERO, ADMIN");
        }
    }

    /**
     * Verificar si un rol existe por id
     * 
     * @param id
     * @return
     */

    public boolean existsById(Integer id) {
        return rolRepository.existsById(id);
    }

    /**
     * Convertir una entidad de rol a un DTO de rol
     * @param rol
     * @return
     */

    private RolDTO convertDTO(Rol rol) {
        return new RolDTO(
                rol.getId(),
                rol.getName(),
                rol.getDescription());
    }

}