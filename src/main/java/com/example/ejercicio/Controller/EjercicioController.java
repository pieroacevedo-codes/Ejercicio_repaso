package com.example.ejercicio.Controller;

import com.example.ejercicio.Dto.CursoDTO;
import com.example.ejercicio.Dto.PersonaCursoDTO;
import com.example.ejercicio.Dto.PersonaDTO;
import com.example.ejercicio.Service.CursoService;
import com.example.ejercicio.Service.PersonaCursoService;
import com.example.ejercicio.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ejercicio")
public class EjercicioController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private PersonaCursoService personaCursoService;

    // POST /ejercicio/personas  {"nombre":"Stevens Adolfo","ap_paterno":"Conde","ap_materno":"Meza","dni":"12345678"}
    @PostMapping("personas")
    public PersonaDTO guardar_persona(@RequestBody PersonaDTO persona) {
        return personaService.guardar(persona);
    }

    // POST /ejercicio/cursos  {"curso":"ARQ WEB","codigo":"V2026"}
    @PostMapping("cursos")
    public CursoDTO guardar_curso(@RequestBody CursoDTO curso) {
        return cursoService.guardar(curso);
    }

    // POST /ejercicio/persona-curso  {"id_persona":1,"id_curso":2}
    @PostMapping("persona-curso")
    public PersonaCursoDTO guardar_persona_curso(@RequestBody PersonaCursoDTO personaCurso) {
        return personaCursoService.guardar(personaCurso);
    }

    // GET /ejercicio/persona-curso
    @GetMapping("persona-curso")
    public List<PersonaCursoDTO> listar_persona_curso() {
        return personaCursoService.listar();
    }

    // DELETE /ejercicio/personas/1  (baja logica)
    @DeleteMapping("personas/{idPersona}")
    public String eliminar_persona(@PathVariable Long idPersona) {
        personaService.eliminar(idPersona);
        return "Persona eliminada";
    }

    // DELETE /ejercicio/cursos/1  (baja logica)
    @DeleteMapping("cursos/{idCurso}")
    public String eliminar_curso(@PathVariable Long idCurso) {
        cursoService.eliminar(idCurso);
        return "Curso eliminado";
    }

    // DELETE /ejercicio/persona-curso/1  (baja logica)
    @DeleteMapping("persona-curso/{idPersonaCurso}")
    public String eliminar_persona_curso(@PathVariable Long idPersonaCurso) {
        personaCursoService.eliminar(idPersonaCurso);
        return "Relación persona-curso eliminada";
    }
}
