package com.senai.jvmamede.consultasmedicas.repository;

import com.senai.jvmamede.consultasmedicas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
