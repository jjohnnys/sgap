package br.com.jjohnnys.sgap_core.paciente.application.dto;

import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.paciente.application.enums.DepenRespEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.EscolaridadeEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.GeneroEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;

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
    Boolean dependente) {


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
            dependente);
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
            paciente.getDependente());
    }
    
}
