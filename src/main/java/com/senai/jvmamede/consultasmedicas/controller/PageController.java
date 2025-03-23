package com.senai.jvmamede.consultasmedicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/pagina/pacientes")
    public String paginaPacientes() {
        return "paciente"; // templates/paciente.html
    }

    @GetMapping("/pagina/medicos")
    public String paginaMedicos() {
        return "medico"; // templates/medico.html
    }

    @GetMapping("/pagina/consultas")
    public String paginaConsultas() {
        return "consultas"; // templates/consultas.html
    }
}
