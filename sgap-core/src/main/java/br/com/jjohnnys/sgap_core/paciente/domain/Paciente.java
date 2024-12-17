package br.com.jjohnnys.sgap_core.paciente.domain;

import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.paciente.application.enums.DepenRespEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Paciente {
    private Long id;
    private String nome;
    private CpfCnpj cpfCnpj;
    private String rg;
    private FisicaJuridicaEnum fisicaJuridica;
    private LocalDate dataNascimento;
    private String escolaridade;
    private String genero;
    private String profissao;
    private String endereco;
    private String status;
    private String observacao;
    private DepenRespEnum depenResp;
}
