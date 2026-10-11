package com.example.ejercicio.Repository;

import com.example.ejercicio.Entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {

    @Query("SELECT COUNT(p) > 0 FROM PersonaEntity p WHERE p.dni = :dni AND p.estado_persona = 1")
    boolean existe_dni(@Param("dni") String dni);
}
