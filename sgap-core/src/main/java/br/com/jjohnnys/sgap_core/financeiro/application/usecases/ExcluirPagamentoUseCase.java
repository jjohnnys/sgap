package br.com.jjohnnys.sgap_core.financeiro.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;

@Service
public class ExcluirPagamentoUseCase {
    
    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;

    public void execute(Long idPagamento) {
        Pagamento pagamento = financeiroDsGateways.findPagamentoById(idPagamento);
        financeiroDsGateways.deletePagamento(pagamento.getId());
    }
    
}
