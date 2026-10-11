package com.example.ejercicio.Controller;

import com.example.ejercicio.Dto.AuditoriaDTO;
import com.example.ejercicio.Service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auditoria")
@Tag(name = "Controlador de auditoría", description = "Permite consultar cuándo se creó o eliminó cada registro")
public class AuditoriaController {

    @Autowired
    private AuditoriaService service;

    @GetMapping("listar")
    @Operation(summary = "Listar los registros de auditoría (tabla, id del registro y fechas)")
    public List<AuditoriaDTO> listar() {
        return service.listar();
    }
}
