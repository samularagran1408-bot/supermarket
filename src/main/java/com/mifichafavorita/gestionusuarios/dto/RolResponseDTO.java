package com.mifichafavorita.gestionusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolResponseDTO {

/**
 * Se guardará la información del rol
 */

    private Integer id;
    private String name;
    private String description;
    private Long rol;

}
