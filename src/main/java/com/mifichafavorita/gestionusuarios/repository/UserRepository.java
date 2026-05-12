package com.mifichafavorita.gestionusuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mifichafavorita.gestionusuarios.entity.Users;

/** Repositorio JPA para la entidad {@link Users}. */
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    /** Busca un usuario por su email. */
    Optional<Users> findByEmail(String email);
}