package com.example.ejercicio.Service.Impl;

import com.example.ejercicio.Dto.AuditoriaDTO;
import com.example.ejercicio.Entity.AuditoriaEntity;
import com.example.ejercicio.Repository.AuditoriaRepository;
import com.example.ejercicio.Service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Registra en tm_auditoria una fila por cada registro creado en las tablas del ejercicio. */
@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    public static final String TABLA_PERSONA = "tm_persona";
    public static final String TABLA_CURSO = "tm_curso";
    public static final String TABLA_PERSONA_CURSO = "tm_persona_curso";

    @Autowired
    private AuditoriaRepository repository;

    @Override
    public void registrar_alta(String tabla, Long idRegistro) {
        AuditoriaEntity auditoria = new AuditoriaEntity();
        auditoria.setTabla_afectada(tabla);
        auditoria.setId_registro(idRegistro);
        auditoria.setFecha_creacion(LocalDateTime.now());
        auditoria.setEstado_auditoria(1);
        repository.save(auditoria);
    }

    @Override
    public void registrar_eliminacion(String tabla, Long idRegistro) {
        // Se actualiza la fila del registro; si no existe (dato anterior a la auditoria) se crea
        AuditoriaEntity auditoria = repository.buscar_registro(tabla, idRegistro).orElseGet(() -> {
            AuditoriaEntity nueva = new AuditoriaEntity();
            nueva.setTabla_afectada(tabla);
            nueva.setId_registro(idRegistro);
            nueva.setFecha_creacion(LocalDateTime.now());
            nueva.setEstado_auditoria(1);
            return nueva;
        });
        auditoria.setFecha_eliminacion(LocalDateTime.now());
        repository.save(auditoria);
    }

    @Override
    public List<AuditoriaDTO> listar() {
        List<AuditoriaEntity> listar = repository.listar_todo();

        List<AuditoriaDTO> listarDto = new ArrayList<>();

        for (AuditoriaEntity auditoriaEntity : listar) {
            AuditoriaDTO auditoriaDto = new AuditoriaDTO();
            auditoriaDto.setId_auditoria(auditoriaEntity.getId_auditoria());
            auditoriaDto.setId_registro(auditoriaEntity.getId_registro());
            auditoriaDto.setTabla_afectada(auditoriaEntity.getTabla_afectada());
            auditoriaDto.setFecha_creacion(auditoriaEntity.getFecha_creacion());
            auditoriaDto.setFecha_edicion(auditoriaEntity.getFecha_edicion());
            auditoriaDto.setFecha_eliminacion(auditoriaEntity.getFecha_eliminacion());
            auditoriaDto.setEstado_auditoria(auditoriaEntity.getEstado_auditoria());
            listarDto.add(auditoriaDto);
        }

        return listarDto;
    }
}
