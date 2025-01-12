package br.com.jjohnnys.sgap_core.paciente.application.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;

public record ResponsavelDTO(
    Long id,
    String nome,
    String cpfCnpnj,
    String rg,
    Character fisicaJuridica,
    LocalDate dataNascimento,
    String profissao,
    String endereco,
    String observacao,
    String email,
    Set<String> telefones) {


    public Responsavel criarResponsavel() {
        return new Responsavel(
            id, 
            nome,
            new CpfCnpj(cpfCnpnj, FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(fisicaJuridica)),
            new Rg(rg),
            FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(fisicaJuridica),
            dataNascimento,
            profissao,
            endereco,
            observacao,
            new Email(email),
            toSetTelefones(telefones));
    }

    public static ResponsavelDTO criarPacienteDTO(Responsavel responsavel) {
        return new ResponsavelDTO(
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
            toListStringTelefones(responsavel.getTelefones()));
    }

    private Set<Telefone> toSetTelefones(Set<String> telefones) {
        return telefones.stream().map(Telefone::new).collect(Collectors.toSet());
    }

    private static Set<String> toListStringTelefones(Set<Telefone> telefones) {
        return telefones.stream().map(Telefone::getValor).collect(Collectors.toSet());
    }
    
}
