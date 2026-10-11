package com.example.ejercicio.Dto;

import lombok.Data;

// Entrada: solo id_persona e id_curso. Salida (listado): ademas trae los nombres.
@Data
public class PersonaCursoDTO {

    private Long id_persona_curso;
    private Long id_persona;
    private Long id_curso;
    private String nombre_persona;
    private String nombre_curso;
}
