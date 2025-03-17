package br.com.jjohnnys.sgap_core.financeiro.application.gateways;

import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;

public interface FinanceiroDsGateways {
    
    
    ModoPagamento saveModoPagamento(ModoPagamento modoPagamento);
    ModoPagamento findModoPagamentoPorIdPaciente(Long idPaciente);
    Pagamento savePagamento(Pagamento pagamento);
    Pagamento findById(Long id);
    Pagamento findByIdPaciente(Long idPaciente);
    void deletePagamento(Long id);



    
}
