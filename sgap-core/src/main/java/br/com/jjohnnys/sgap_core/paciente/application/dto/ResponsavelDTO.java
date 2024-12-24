package br.com.jjohnnys.sgap_core.paciente.application.dto;

import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;

public record ResponsavelDTO(
    Long id,
    String nome,
    String cpfCnpnj,
    String rg,
    Character fisicaJuridica,
    LocalDate dataNascimento,
    String profissao,
    String endereco,
    String observacao) {


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
            observacao);
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
            responsavel.getObservacao());
    }
    
}
