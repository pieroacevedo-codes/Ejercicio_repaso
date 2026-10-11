package com.example.ejercicio.Service.Impl;

import com.example.ejercicio.Dto.CursoDTO;
import com.example.ejercicio.Entity.CursoEntity;
import com.example.ejercicio.Repository.CursoRepository;
import com.example.ejercicio.Repository.PersonaCursoRepository;
import com.example.ejercicio.Service.AuditoriaService;
import com.example.ejercicio.Service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private PersonaCursoRepository personaCursoRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    @Transactional
    public CursoDTO guardar(CursoDTO curso) {
        if (curso.getCurso() == null || curso.getCurso().isBlank()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        if (curso.getCodigo() == null || curso.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El código del curso es obligatorio");
        }
        if (repository.existe_codigo(curso.getCodigo().trim())) {
            throw new IllegalArgumentException("Ya existe un curso con ese código");
        }

        CursoEntity nuevo = new CursoEntity();
        nuevo.setCurso(curso.getCurso().trim());
        nuevo.setCodigo(curso.getCodigo().trim());
        nuevo.setEstado_curso(1);

        nuevo = repository.save(nuevo);
        auditoriaService.registrar_alta(AuditoriaServiceImpl.TABLA_CURSO, nuevo.getId_curso());

        CursoDTO dto = new CursoDTO();
        dto.setId_curso(nuevo.getId_curso());
        dto.setCurso(nuevo.getCurso());
        dto.setCodigo(nuevo.getCodigo());
        return dto;
    }

    @Override
    @Transactional
    public void eliminar(Long idCurso) {
        CursoEntity registro = repository.findById(idCurso)
                .filter(r -> Integer.valueOf(1).equals(r.getEstado_curso()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        if (personaCursoRepository.curso_tiene_personas(idCurso)) {
            throw new IllegalArgumentException("No se puede eliminar: el curso tiene personas asignadas");
        }

        registro.setEstado_curso(0);
        repository.save(registro);
        auditoriaService.registrar_eliminacion(AuditoriaServiceImpl.TABLA_CURSO, registro.getId_curso());
    }
}
