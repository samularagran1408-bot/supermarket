package com.mifichafavorita.gestionusuarios.dto;

import lombok.Data;

@Data
public class RolRequestDTO {

    /**
     * Se guardará la información que tendrá cada objeto DTO
     */

    private String name;
    private String description;
    private Long userId;

}
