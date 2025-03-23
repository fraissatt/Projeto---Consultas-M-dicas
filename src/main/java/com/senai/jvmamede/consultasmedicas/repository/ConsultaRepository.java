package com.senai.jvmamede.consultasmedicas.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.jvmamede.consultasmedicas.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Optional<Consulta> findByPacienteIdAndDataAndHorario(Long pacienteId, LocalDate data, LocalTime horario);
}
