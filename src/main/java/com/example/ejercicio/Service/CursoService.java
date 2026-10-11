package com.example.ejercicio.Service;

import com.example.ejercicio.Dto.CursoDTO;

public interface CursoService {

    CursoDTO guardar(CursoDTO curso);

    // Baja logica (estado 0); se audita la eliminacion
    void eliminar(Long idCurso);
}
