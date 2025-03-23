package com.senai.jvmamede.consultasmedicas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaDTO {

    private Long id;
    private LocalDate data;
    private LocalTime horario;
    private String status;

    private Long medicoId;
    private Long pacienteId;

    public ConsultaDTO() {}

    public ConsultaDTO(Long id, LocalDate data, LocalTime horario, String status, Long medicoId, Long pacienteId) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.status = status;
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
    }

    // Getters e Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
