package com.mifichafavorita.gestionusuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mifichafavorita.gestionusuarios.entity.Rol;
import com.mifichafavorita.gestionusuarios.entity.Rol.RolName;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Busca rol por atributo 
     * @param name
     * @return
     */

    Optional<Rol> findByName(RolName name);

    Optional<Rol> findById(Integer id);
}