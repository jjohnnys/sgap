package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways;

import org.springframework.stereotype.Component;

import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.ModoPagamentoJDBC;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FinanceiroRepositoryGateway implements FinanceiroDsGateways {

    private final ModoPagamentoJDBC modoPagamentoJDBC;

    @Override
    public ModoPagamento saveModoPagamento(ModoPagamento modoPagamento) {
        return modoPagamentoJDBC.insert(modoPagamento);
    }

    @Override
    public ModoPagamento findModoPagamentoPorIdPaciente(Long idPaciente) {
        return modoPagamentoJDBC.findByIdPaciente(idPaciente).get();
    }


    
}
