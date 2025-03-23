package br.com.jjohnnys.sgap_core.financeiro.application.presenter;

import java.math.BigDecimal;
import java.sql.Date;

public record PagamentoPresenter(String nomePaciente, String cpfCnpj,  Date data, BigDecimal valor, String status) {}
