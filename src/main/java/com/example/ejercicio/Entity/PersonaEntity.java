package com.example.ejercicio.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tm_persona", schema = "ejercicio")
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id_persona;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ap_paterno")
    private String ap_paterno;

    @Column(name = "ap_materno")
    private String ap_materno;

    @Column(name = "dni")
    private String dni;

    @Column(name = "estado_persona")
    private Integer estado_persona;
}
