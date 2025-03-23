package br.com.jjohnnys.sgap_core.financeiro.application.gateways;

import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;

import java.util.List;

import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;

public interface FinanceiroDsGateways {
    
    
    PlanoAtendimento savePlanoAtendimento(PlanoAtendimento modoPagamento);
    PlanoAtendimento findPlanoAtendimentoPorIdPaciente(Long idPaciente);
    Pagamento savePagamento(Pagamento pagamento);
    Pagamento findPagamentoById(Long id);
    List<Pagamento> findByIdPaciente(Long idPaciente);
    void deletePagamento(Long id);



    
}
