package com.example.ejercicio.Dto;

import lombok.Data;

@Data
public class PersonaDTO {

    private Long id_persona;
    private String nombre;
    private String ap_paterno;
    private String ap_materno;
    private String dni;
}
