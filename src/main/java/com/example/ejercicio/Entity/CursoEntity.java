package com.example.ejercicio.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tm_curso", schema = "ejercicio")
public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long id_curso;

    @Column(name = "curso")
    private String curso;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "estado_curso")
    private Integer estado_curso;
}
