package br.com.jjohnnys.sgap_core.financeiro.application.presenter;

import java.math.BigDecimal;

public record RelatorioPagamentoPresenter(String nomePaciente, String cnpjCpf, int mes, int ano, BigDecimal valor, String status, BigDecimal total) {

    
}
