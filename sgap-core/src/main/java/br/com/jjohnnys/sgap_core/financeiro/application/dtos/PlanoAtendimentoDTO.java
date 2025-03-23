package br.com.jjohnnys.sgap_core.financeiro.application.dtos;

import java.math.BigDecimal;

import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;

public record PlanoAtendimentoDTO(Long id, Long idPaciente, String plano, BigDecimal valor, Integer diaDoMes) {

    

    public static PlanoAtendimentoDTO criarModoPagamentoDTO(PlanoAtendimento modoPagamento) {
        return new PlanoAtendimentoDTO(modoPagamento.getId(), modoPagamento.getPaciente().getId(), modoPagamento.getPlano().name(), modoPagamento.getValorPorConsulta(), modoPagamento.getDiaPagamento());
    }
    
}
