package br.com.jjohnnys.sgap_core.paciente.application.dto;

import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.paciente.application.enums.DepenRespEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;

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
    String depenResp) {


    public Paciente criarPaciente() {
        return new Paciente(id, nome, new CpfCnpj(cpfCnpnj, FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(fisicaJuridica)) , rg,  FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(fisicaJuridica), dataNascimento, escolaridade, genero, profissao, endereco, status, observacao, DepenRespEnum.getDepenRespEnumPorValor(depenResp));
    }

    public static PacienteDTO criarPacienteDTO(Paciente paciente) {
        return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCpfCnpj().getValor() , paciente.getRg(), paciente.getFisicaJuridica().getValor(), paciente.getDataNascimento(), paciente.getEscolaridade(), paciente.getGenero(), paciente.getProfissao(), paciente.getEndereco(), paciente.getStatus(), paciente.getObservacao(), paciente.getDepenResp().getValor());
    }
    
}
