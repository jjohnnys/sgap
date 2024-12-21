package br.com.jjohnnys.sgap_core.paciente.domain;

import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.paciente.application.enums.DepenRespEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.EscolaridadeEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.GeneroEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Paciente {
    private Long id;
    private String nome;
    private CpfCnpj cpfCnpj;
    private Rg rg;
    private FisicaJuridicaEnum fisicaJuridica;
    private LocalDate dataNascimento;
    private EscolaridadeEnum escolaridade;
    private GeneroEnum genero;
    private String profissao;
    private String endereco;
    private StatusAtendimentoEnum status;
    private String observacao;
    private DepenRespEnum depenResp;
}
