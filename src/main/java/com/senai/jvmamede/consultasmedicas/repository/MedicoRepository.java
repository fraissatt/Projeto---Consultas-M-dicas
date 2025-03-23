package com.senai.jvmamede.consultasmedicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.jvmamede.consultasmedicas.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
