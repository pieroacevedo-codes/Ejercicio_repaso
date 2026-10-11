package com.example.ejercicio.Service;

import com.example.ejercicio.Dto.PersonaCursoDTO;

import java.util.List;

public interface PersonaCursoService {

    List<PersonaCursoDTO> listar();

    PersonaCursoDTO guardar(PersonaCursoDTO personaCurso);

    // Baja logica (estado 0); se audita la eliminacion
    void eliminar(Long idPersonaCurso);
}
