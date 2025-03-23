package com.senai.jvmamede.consultasmedicas.controller;

import com.senai.jvmamede.consultasmedicas.dto.PacienteDTO;
import com.senai.jvmamede.consultasmedicas.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<PacienteDTO> listarTodos() {
        return pacienteService.listarTodos();
    }

    @PostMapping
    public PacienteDTO cadastrar(@RequestBody PacienteDTO dto) {
        return pacienteService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public PacienteDTO atualizar(@PathVariable Long id, @RequestBody PacienteDTO dto) {
        return pacienteService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        pacienteService.excluir(id);
    }
}
