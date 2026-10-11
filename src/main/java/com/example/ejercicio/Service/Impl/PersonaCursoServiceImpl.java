package com.example.ejercicio.Service.Impl;

import com.example.ejercicio.Dto.PersonaCursoDTO;
import com.example.ejercicio.Entity.CursoEntity;
import com.example.ejercicio.Entity.PersonaCursoEntity;
import com.example.ejercicio.Entity.PersonaEntity;
import com.example.ejercicio.Repository.CursoRepository;
import com.example.ejercicio.Repository.PersonaCursoRepository;
import com.example.ejercicio.Repository.PersonaRepository;
import com.example.ejercicio.Service.AuditoriaService;
import com.example.ejercicio.Service.PersonaCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaCursoServiceImpl implements PersonaCursoService {

    @Autowired
    private PersonaCursoRepository repository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    @Transactional(readOnly = true)
    public List<PersonaCursoDTO> listar() {
        List<PersonaCursoEntity> listar = repository.listar_activos();

        List<PersonaCursoDTO> listarDto = new ArrayList<>();

        for (PersonaCursoEntity personaCursoEntity : listar) {
            listarDto.add(convertir(personaCursoEntity));
        }

        return listarDto;
    }

    @Override
    @Transactional
    public PersonaCursoDTO guardar(PersonaCursoDTO personaCurso) {
        if (personaCurso.getId_persona() == null || personaCurso.getId_curso() == null) {
            throw new IllegalArgumentException("id_persona e id_curso son obligatorios");
        }

        PersonaEntity persona = personaRepository.findById(personaCurso.getId_persona())
                .filter(p -> Integer.valueOf(1).equals(p.getEstado_persona()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
        CursoEntity curso = cursoRepository.findById(personaCurso.getId_curso())
                .filter(c -> Integer.valueOf(1).equals(c.getEstado_curso()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        if (repository.existe_relacion(persona.getId_persona(), curso.getId_curso())) {
            throw new IllegalArgumentException("La persona ya está registrada en ese curso");
        }

        PersonaCursoEntity nueva = new PersonaCursoEntity();
        nueva.setPersona(persona);
        nueva.setCurso(curso);
        nueva.setEstado_persona_curso(1);

        nueva = repository.save(nueva);
        auditoriaService.registrar_alta(AuditoriaServiceImpl.TABLA_PERSONA_CURSO, nueva.getId_persona_curso());

        return convertir(nueva);
    }

    @Override
    @Transactional
    public void eliminar(Long idPersonaCurso) {
        PersonaCursoEntity registro = repository.findById(idPersonaCurso)
                .filter(r -> Integer.valueOf(1).equals(r.getEstado_persona_curso()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));

        registro.setEstado_persona_curso(0);
        repository.save(registro);
        auditoriaService.registrar_eliminacion(AuditoriaServiceImpl.TABLA_PERSONA_CURSO, registro.getId_persona_curso());
    }

    private PersonaCursoDTO convertir(PersonaCursoEntity personaCurso) {
        PersonaCursoDTO dto = new PersonaCursoDTO();
        dto.setId_persona_curso(personaCurso.getId_persona_curso());
        dto.setId_persona(personaCurso.getPersona().getId_persona());
        dto.setId_curso(personaCurso.getCurso().getId_curso());
        dto.setNombre_persona(personaCurso.getPersona().getNombre() + " "
                + personaCurso.getPersona().getAp_paterno() + " " + personaCurso.getPersona().getAp_materno());
        dto.setNombre_curso(personaCurso.getCurso().getCurso());
        return dto;
    }
}
