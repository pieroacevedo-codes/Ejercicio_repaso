package com.example.ejercicio.Repository;

import com.example.ejercicio.Entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long> {

    @Query("SELECT COUNT(c) > 0 FROM CursoEntity c WHERE LOWER(c.codigo) = LOWER(:codigo) AND c.estado_curso = 1")
    boolean existe_codigo(@Param("codigo") String codigo);
}
