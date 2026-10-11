package com.example.ejercicio.Service;

import com.example.ejercicio.Dto.AuditoriaDTO;

import java.util.List;

public interface AuditoriaService {

    void registrar_alta(String tabla, Long idRegistro);

    void registrar_eliminacion(String tabla, Long idRegistro);

    List<AuditoriaDTO> listar();
}
