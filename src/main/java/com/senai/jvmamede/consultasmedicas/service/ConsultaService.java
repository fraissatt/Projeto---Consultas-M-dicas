package com.senai.jvmamede.consultasmedicas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senai.jvmamede.consultasmedicas.dto.ConsultaDTO;
import com.senai.jvmamede.consultasmedicas.entity.Consulta;
import com.senai.jvmamede.consultasmedicas.entity.Medico;
import com.senai.jvmamede.consultasmedicas.entity.Paciente;
import com.senai.jvmamede.consultasmedicas.repository.ConsultaRepository;
import com.senai.jvmamede.consultasmedicas.repository.MedicoRepository;
import com.senai.jvmamede.consultasmedicas.repository.PacienteRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<ConsultaDTO> listarTodas() {
        return consultaRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public ConsultaDTO agendar(ConsultaDTO dto) {
        Optional<Consulta> existente = consultaRepository
            .findByPacienteIdAndDataAndHorario(dto.getPacienteId(), dto.getData(), dto.getHorario());
    
        if (existente.isPresent()) {
            throw new RuntimeException("Já existe uma consulta agendada para essa data e horário.");
        }
    
        Medico medico = medicoRepository.findById(dto.getMedicoId())
            .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    
        Consulta novaConsulta = new Consulta();
        novaConsulta.setData(dto.getData());
        novaConsulta.setHorario(dto.getHorario());
        novaConsulta.setStatus("agendada");
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
    
        return converterParaDTO(consultaRepository.save(novaConsulta));
    }    

    public ConsultaDTO cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setStatus("cancelada");
        return converterParaDTO(consultaRepository.save(consulta));
    }

    private ConsultaDTO converterParaDTO(Consulta consulta) {
        return new ConsultaDTO(
                consulta.getId(),
                consulta.getData(),
                consulta.getHorario(),
                consulta.getStatus(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId()
        );
    }

    public void marcarComoRealizada(Long id) {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        
        if (!"agendada".equals(consulta.getStatus())) {
            throw new RuntimeException("Apenas consultas agendadas podem ser realizadas.");
        }

        consulta.setStatus("realizada");
        consultaRepository.save(consulta);
    }

    public void excluirConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        
        if (!"cancelada".equals(consulta.getStatus())) {
            throw new RuntimeException("Apenas consultas canceladas podem ser excluídas.");
        }

        consultaRepository.deleteById(id);
    }

}
