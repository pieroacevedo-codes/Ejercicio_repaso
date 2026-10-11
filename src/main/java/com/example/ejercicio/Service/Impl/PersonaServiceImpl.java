package com.example.ejercicio.Service.Impl;

import com.example.ejercicio.Dto.PersonaDTO;
import com.example.ejercicio.Entity.PersonaEntity;
import com.example.ejercicio.Repository.PersonaCursoRepository;
import com.example.ejercicio.Repository.PersonaRepository;
import com.example.ejercicio.Service.AuditoriaService;
import com.example.ejercicio.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository repository;

    @Autowired
    private PersonaCursoRepository personaCursoRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    @Transactional
    public PersonaDTO guardar(PersonaDTO persona) {
        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (persona.getAp_paterno() == null || persona.getAp_paterno().isBlank()) {
            throw new IllegalArgumentException("El apellido paterno es obligatorio");
        }
        if (persona.getAp_materno() == null || persona.getAp_materno().isBlank()) {
            throw new IllegalArgumentException("El apellido materno es obligatorio");
        }
        if (persona.getDni() == null || !persona.getDni().trim().matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos");
        }
        if (repository.existe_dni(persona.getDni().trim())) {
            throw new IllegalArgumentException("Ya existe una persona con ese DNI");
        }

        PersonaEntity nueva = new PersonaEntity();
        nueva.setNombre(persona.getNombre().trim());
        nueva.setAp_paterno(persona.getAp_paterno().trim());
        nueva.setAp_materno(persona.getAp_materno().trim());
        nueva.setDni(persona.getDni().trim());
        nueva.setEstado_persona(1);

        nueva = repository.save(nueva);
        auditoriaService.registrar_alta(AuditoriaServiceImpl.TABLA_PERSONA, nueva.getId_persona());

        return convertir(nueva);
    }

    @Override
    @Transactional
    public void eliminar(Long idPersona) {
        PersonaEntity registro = repository.findById(idPersona)
                .filter(r -> Integer.valueOf(1).equals(r.getEstado_persona()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));

        if (personaCursoRepository.persona_tiene_cursos(idPersona)) {
            throw new IllegalArgumentException("No se puede eliminar: la persona tiene cursos asignados");
        }

        registro.setEstado_persona(0);
        repository.save(registro);
        auditoriaService.registrar_eliminacion(AuditoriaServiceImpl.TABLA_PERSONA, registro.getId_persona());
    }

    private PersonaDTO convertir(PersonaEntity persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId_persona(persona.getId_persona());
        dto.setNombre(persona.getNombre());
        dto.setAp_paterno(persona.getAp_paterno());
        dto.setAp_materno(persona.getAp_materno());
        dto.setDni(persona.getDni());
        return dto;
    }
}
