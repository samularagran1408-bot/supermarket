package com.mifichafavorita.gestionusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO {

    private Integer id;
    private String name;
    private String description;

}
