package br.com.jjohnnys.sgap_core.financeiro.application.dtos;

import java.math.BigDecimal;

import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;

public record ModoPagamentoDTO(Long id, Long idPaciente, String plano, BigDecimal valor, Integer diaDoMes) {

    public ModoPagamento criarModoPagamento() {
         return new ModoPagamento(id, idPaciente, PlanoEnum.getGeneroEnumPorValor(plano) , valor, diaDoMes);
    }

    public static ModoPagamentoDTO criarModoPagamentoDTO(ModoPagamento modoPagamento) {
        return new ModoPagamentoDTO(modoPagamento.getId(), modoPagamento.getIdPaciente(), modoPagamento.getPlano().name(), modoPagamento.getValor(), modoPagamento.getDiaDoMes());
    }
    
}
