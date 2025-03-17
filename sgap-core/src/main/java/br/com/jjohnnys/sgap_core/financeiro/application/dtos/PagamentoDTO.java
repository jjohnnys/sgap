package br.com.jjohnnys.sgap_core.financeiro.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;

public record PagamentoDTO(Long id, Long idPaciente, LocalDate data, BigDecimal valor, String status) {

    public Pagamento criarPagamento() {
        return new Pagamento(id, idPaciente, data, valor, StatusPagamentoEnum.getStatusPagamentoEnum(status));
    }

    public static PagamentoDTO criarPagamentoDTO(Pagamento pagamento) {
        return new PagamentoDTO(pagamento.getId(), pagamento.getIdPaciente(), pagamento.getData(), pagamento.getValor(), pagamento.getStatus().name());
    }
    
}
