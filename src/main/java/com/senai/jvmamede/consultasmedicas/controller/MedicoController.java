package com.senai.jvmamede.consultasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.senai.jvmamede.consultasmedicas.dto.MedicoDTO;
import com.senai.jvmamede.consultasmedicas.service.MedicoService;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/pagina")
    public ModelAndView exibirPaginaDeMedicos() {
        return new ModelAndView("medico");
    }

    @GetMapping
    @ResponseBody
    public List<MedicoDTO> listarTodos() {
        return medicoService.listarTodos();
    }

    @PostMapping
    @ResponseBody
    public MedicoDTO cadastrar(@RequestBody MedicoDTO dto) {
        return medicoService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public MedicoDTO atualizar(@PathVariable Long id, @RequestBody MedicoDTO dto) {
        return medicoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void excluir(@PathVariable Long id) {
        medicoService.excluir(id);
    }
}
