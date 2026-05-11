package com.mifichafavorita.gestionusuarios.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mifichafavorita.gestionusuarios.dto.HttpGlobalResponse;
import com.mifichafavorita.gestionusuarios.dto.JwtDTO;
import com.mifichafavorita.gestionusuarios.dto.LoginRequestDTO;
import com.mifichafavorita.gestionusuarios.dto.RegisterRequestDTO;
import com.mifichafavorita.gestionusuarios.dto.RegisterResponseDTO;
import com.mifichafavorita.gestionusuarios.entity.Rol;
import com.mifichafavorita.gestionusuarios.entity.Users;
import com.mifichafavorita.gestionusuarios.repository.RolRepository;
import com.mifichafavorita.gestionusuarios.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
/*  */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;  // ← Agrega esto
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        RegisterResponseDTO response = new RegisterResponseDTO();

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            response.setMessage("El correo ya está en uso");
            return response;
        }

        // 🔧 Obtener el rol según lo que envía el usuario
        Rol rol;
        if (request.getRol() != null) {
            // Convertir Long a Integer (el ID del rol)
            Integer rolId = request.getRol().intValue();
            rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
        } else {
            // Buscar CLIENTE por nombre (no por ID)
            rol = rolRepository.findByName(Rol.RolName.cliente)
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user.setRol(rol);  // ← Asignar el rol
        
        userRepository.save(user);

        response.setMessage("Se ha registrado correctamente");
        return response;
    }

    public HttpGlobalResponse<JwtDTO> login(LoginRequestDTO request) {
        HttpGlobalResponse<JwtDTO> response = new HttpGlobalResponse<>();
        Optional<Users> userFound = userRepository.findByEmail(request.getEmail());

        if (userFound.isEmpty()) {
            response.setMessage("Este usuario no se encuentra registrado");
            return response;
        }

        Users user = userFound.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.setMessage("Correo o contraseña son incorrectos");
            return response;
        }

        JwtDTO jwtDTO = new JwtDTO();
        String jwt = jwtService.generateToken(user.getId(), user.getEmail());
        jwtDTO.setJwt(jwt);
        response.setMessage("Inicio de sesión exitoso");
        response.setData(jwtDTO);
        return response;
    }

    public JwtDTO refreshToken(String token) throws Exception {
        JwtDTO response = new JwtDTO();
        String jwt = jwtService.refreshToken(token);
        response.setJwt(jwt);
        return response;
    }
}