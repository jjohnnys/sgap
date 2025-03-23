package br.com.jjohnnys.sgap_core.financeiro.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RelatorioPagamentoDTO {

    private String nomePaciente; 
    private String cnpjCpf; 
    private Integer mesInicio; 
    private Integer mesFim;
    private Integer anoInicio;
    private Integer anoFim;
    private String statusPagamento;

}
