package com.mifichafavorita.gestionusuarios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mifichafavorita.gestionusuarios.dto.UserResponseDTO;
import com.mifichafavorita.gestionusuarios.entity.Users;
import com.mifichafavorita.gestionusuarios.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Repositorio del usuario
     */
    private final UserRepository userRepository;

    public List<UserResponseDTO> listUsers() {
        List<Users> usersFound = userRepository.findAll();
        List<UserResponseDTO> response = new ArrayList<>();


 /**
 * Conversión de entidades Users a DTOs de respuesta.
 */
        for (Users users : usersFound) {
            UserResponseDTO user = new UserResponseDTO();
            user.setId(users.getId());
            user.setName(users.getName());
            user.setEmail(users.getEmail());
            if (users.getRol() != null) {
                user.setRol(users.getRol().getName().name());
            }
            response.add(user);
        }

        return response;
    }

    public Users getUsuarioFromToken(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("No hay usuario autenticado");
        }
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
