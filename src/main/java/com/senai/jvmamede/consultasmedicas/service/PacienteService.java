package com.senai.jvmamede.consultasmedicas.service;

import com.senai.jvmamede.consultasmedicas.dto.PacienteDTO;
import com.senai.jvmamede.consultasmedicas.entity.Paciente;
import com.senai.jvmamede.consultasmedicas.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO cadastrar(PacienteDTO dto) {
        Paciente paciente = converterParaEntidade(dto);
        Paciente salvo = pacienteRepository.save(paciente);
        return converterParaDTO(salvo);
    }

    public PacienteDTO atualizar(Long id, PacienteDTO dto) {
        Optional<Paciente> optional = pacienteRepository.findById(id);
        if (optional.isPresent()) {
            Paciente paciente = optional.get();
            paciente.setNome(dto.getNome());
            paciente.setCpf(dto.getCpf());
            paciente.setDataNascimento(dto.getDataNascimento());
            paciente.setTelefone(dto.getTelefone());
            return converterParaDTO(pacienteRepository.save(paciente));
        } else {
            throw new RuntimeException("Paciente não encontrado com ID: " + id);
        }
    }

    public void excluir(Long id) {
        pacienteRepository.deleteById(id);
    }

    // Conversões
    private PacienteDTO converterParaDTO(Paciente paciente) {
        return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getDataNascimento(), paciente.getTelefone());
    }

    private Paciente converterParaEntidade(PacienteDTO dto) {
        return new Paciente(dto.getId(), dto.getNome(), dto.getCpf(), dto.getDataNascimento(), dto.getTelefone());
    }
}
