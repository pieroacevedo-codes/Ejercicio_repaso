package com.example.ejercicio.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tm_persona_curso", schema = "ejercicio")
public class PersonaCursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_curso")
    private Long id_persona_curso;

    @Column(name = "estado_persona_curso")
    private Integer estado_persona_curso;

    @ManyToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private PersonaEntity persona;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    private CursoEntity curso;
}
