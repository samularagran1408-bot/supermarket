package com.mifichafavorita.gestionusuarios.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mifichafavorita.gestionusuarios.dto.RolResponseDTO;
import com.mifichafavorita.gestionusuarios.entity.Rol;
import com.mifichafavorita.gestionusuarios.repository.RolRepository;

import lombok.RequiredArgsConstructor;

// findByName

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

    public List<RolResponseDTO> listAll() {
        return rolRepository.findAll()
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca por id
     * 
     * @param id
     * @return
     */

    public Optional<RolResponseDTO> getById(Integer id) {
        return rolRepository.findById(id)
                .map(this::convertDTO);
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
            throw new RuntimeException("invalid role: " + name + "The allowed roles are: cliente, cajero, admin");
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
     * 
     * @param rol
     * @return
     */

    private RolResponseDTO convertDTO(Rol rol) {
        return new RolResponseDTO(
                rol.getId(),
                rol.getName().name(),
                rol.getDescription(), null);
    }

}