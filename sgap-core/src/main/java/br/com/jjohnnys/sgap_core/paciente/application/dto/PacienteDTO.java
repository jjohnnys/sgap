package br.com.jjohnnys.sgap_core.paciente.application.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.EscolaridadeEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.GeneroEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;

public record PacienteDTO(
    Long id,
    String nome,
    String cpfCnpnj,
    String rg,
    Character fisicaJuridica,
    LocalDate dataNascimento,
    String escolaridade,
    String genero,
    String profissao,
    String endereco,
    String status,
    String observacao,
    Boolean dependente,
    Set<ResponsavelDTO> responsaveisDTOs,
    String email,
    Set<String> telefones) {


    public Paciente criarPaciente() {
        return new Paciente(
            id, 
            nome,
            new CpfCnpj(cpfCnpnj, FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(fisicaJuridica)),
            new Rg(rg),
            FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(fisicaJuridica),
            dataNascimento,
            EscolaridadeEnum.getEscolaridadeEnumPorValor(escolaridade),
            GeneroEnum.getGeneroEnumPorValor(genero),
            profissao,
            endereco,
            StatusAtendimentoEnum.getStatusAtendimentoEnumPorValor(status),
            observacao,
            dependente,
            getResponsaveis(),
            new Email(email),
            toSetTelefones(telefones));
    }

    public static PacienteDTO criarPacienteDTO(Paciente paciente) {
        return new PacienteDTO(
            paciente.getId(),
            paciente.getNome(),
            paciente.getCpfCnpj().getValor(),
            paciente.getRg().getValor(),
            paciente.getFisicaJuridica().getValor(),
            paciente.getDataNascimento(),
            paciente.getEscolaridade().getValor(),
            paciente.getGenero().getValor(),
            paciente.getProfissao(),
            paciente.getEndereco(),
            paciente.getStatus().getValor(),
            paciente.getObservacao(),
            paciente.isDependente(),
            getResponsaveisDTOs(paciente.getResponsaveis()),
            paciente.getEmail().getValor(),
            toListStringTelefones(paciente.getTelefones()));
    }

    private Set<Responsavel> getResponsaveis() {
        if(responsaveisDTOs == null) return null;
        return responsaveisDTOs.stream()
        .map( responsavelDTO -> 
        new Responsavel(
            responsavelDTO.id(),
            responsavelDTO.nome(),
            new CpfCnpj(responsavelDTO.cpfCnpnj(), FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(responsavelDTO.fisicaJuridica())),
            new Rg(responsavelDTO.rg()),
            FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(responsavelDTO.fisicaJuridica()),
            responsavelDTO.dataNascimento(),
            responsavelDTO.profissao(),
            responsavelDTO.endereco(),
            responsavelDTO.observacao(),
            new Email(responsavelDTO.email()),
            toSetTelefones(telefones))).collect(Collectors.toSet());
    }

    public static Set<ResponsavelDTO> getResponsaveisDTOs(Set<Responsavel> responsavels) {
        if(responsavels == null) return null;
        return responsavels.stream()
                .map( responsavel -> 
                new ResponsavelDTO(
                    responsavel.getId(),
                    responsavel.getNome(),
                    responsavel.getCpfCnpj().getValor(),
                    responsavel.getRg().getValor(),
                    responsavel.getFisicaJuridica().getValor(),
                    responsavel.getDataNascimento(),
                    responsavel.getProfissao(),
                    responsavel.getEndereco(),
                    responsavel.getObservacao(),
                    responsavel.getEmail().getValor(),
                    toListStringTelefones(responsavel.getTelefones()))).collect(Collectors.toSet());
    }

    private Set<Telefone> toSetTelefones(Set<String> telefones) {
        return telefones.stream().map(Telefone::new).collect(Collectors.toSet());
    }

    private static Set<String> toListStringTelefones(Set<Telefone> telefones) {
        return telefones.stream().map(Telefone::getValor).collect(Collectors.toSet());
    }
    
    
}
