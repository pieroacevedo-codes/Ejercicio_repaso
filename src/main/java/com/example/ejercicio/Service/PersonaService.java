package com.example.ejercicio.Service;

import com.example.ejercicio.Dto.PersonaDTO;

public interface PersonaService {

    PersonaDTO guardar(PersonaDTO persona);

    // Baja logica (estado 0); se audita la eliminacion
    void eliminar(Long idPersona);
}
