package br.com.jjohnnys.sgap_core.financeiro.application.presenter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class PagamentoPresenter {   
    @Getter
    @Setter         
    BigDecimal total;
    @Getter
    @Setter
    List<DadosPagamentoPaciente> dadosPaciente;
    @Getter
    @AllArgsConstructor
    public class DadosPagamentoPaciente {
        String nomePaciente;
        String cpfCnpj;
        Date data;
        BigDecimal valor;
        String status;
    }
}
