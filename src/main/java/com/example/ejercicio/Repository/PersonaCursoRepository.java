package com.example.ejercicio.Repository;

import com.example.ejercicio.Entity.PersonaCursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaCursoRepository extends JpaRepository<PersonaCursoEntity, Long> {

    @Query("SELECT pc FROM PersonaCursoEntity pc JOIN FETCH pc.persona JOIN FETCH pc.curso " +
            "WHERE pc.estado_persona_curso = 1 ORDER BY pc.id_persona_curso")
    List<PersonaCursoEntity> listar_activos();

    @Query("SELECT COUNT(pc) > 0 FROM PersonaCursoEntity pc WHERE pc.persona.id_persona = :idPersona " +
            "AND pc.curso.id_curso = :idCurso AND pc.estado_persona_curso = 1")
    boolean existe_relacion(@Param("idPersona") Long idPersona, @Param("idCurso") Long idCurso);

    @Query("SELECT COUNT(pc) > 0 FROM PersonaCursoEntity pc WHERE pc.persona.id_persona = :idPersona " +
            "AND pc.estado_persona_curso = 1")
    boolean persona_tiene_cursos(@Param("idPersona") Long idPersona);

    @Query("SELECT COUNT(pc) > 0 FROM PersonaCursoEntity pc WHERE pc.curso.id_curso = :idCurso " +
            "AND pc.estado_persona_curso = 1")
    boolean curso_tiene_personas(@Param("idCurso") Long idCurso);
}
