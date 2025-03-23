package com.senai.jvmamede.consultasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.jvmamede.consultasmedicas.dto.ConsultaDTO;
import com.senai.jvmamede.consultasmedicas.service.ConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<ConsultaDTO> listarTodas() {
        return consultaService.listarTodas();
    }

    @PostMapping("/agendar")
    public ConsultaDTO agendar(@RequestBody ConsultaDTO dto) {
        return consultaService.agendar(dto);
    }

    @PutMapping("/{id}/cancelar")
    public ConsultaDTO cancelar(@PathVariable Long id) {
        return consultaService.cancelar(id);
    }

    @PutMapping("/{id}/realizar")
    public void realizar(@PathVariable Long id) {
        consultaService.marcarComoRealizada(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        consultaService.excluirConsulta(id);
    }

}
