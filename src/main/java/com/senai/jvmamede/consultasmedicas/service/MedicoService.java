package com.senai.jvmamede.consultasmedicas.service;

import com.senai.jvmamede.consultasmedicas.dto.MedicoDTO;
import com.senai.jvmamede.consultasmedicas.entity.Medico;
import com.senai.jvmamede.consultasmedicas.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public List<MedicoDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public MedicoDTO cadastrar(MedicoDTO dto) {
        Medico medico = converterParaEntidade(dto);
        Medico salvo = medicoRepository.save(medico);
        return converterParaDTO(salvo);
    }

    public MedicoDTO atualizar(Long id, MedicoDTO dto) {
        Optional<Medico> optional = medicoRepository.findById(id);
        if (optional.isPresent()) {
            Medico medico = optional.get();
            medico.setNome(dto.getNome());
            medico.setEspecialidade(dto.getEspecialidade());
            medico.setCrm(dto.getCrm());
            return converterParaDTO(medicoRepository.save(medico));
        } else {
            throw new RuntimeException("Médico não encontrado com ID: " + id);
        }
    }

    public void excluir(Long id) {
        medicoRepository.deleteById(id);
    }

    // Conversões entre Entidade e DTO
    private MedicoDTO converterParaDTO(Medico medico) {
        return new MedicoDTO(medico.getId(), medico.getNome(), medico.getEspecialidade(), medico.getCrm());
    }

    private Medico converterParaEntidade(MedicoDTO dto) {
        return new Medico(dto.getId(), dto.getNome(), dto.getEspecialidade(), dto.getCrm());
    }
}
