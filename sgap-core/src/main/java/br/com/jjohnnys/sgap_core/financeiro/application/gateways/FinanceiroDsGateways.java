package br.com.jjohnnys.sgap_core.financeiro.application.gateways;

import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;

public interface FinanceiroDsGateways {
    
    
    ModoPagamento saveModoPagamento(ModoPagamento modoPagamento);
    ModoPagamento findModoPagamentoPorIdPaciente(Long idPaciente);


    
}
