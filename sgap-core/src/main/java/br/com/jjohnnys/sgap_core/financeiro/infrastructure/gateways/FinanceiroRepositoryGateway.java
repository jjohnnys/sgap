package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways;

import org.springframework.stereotype.Component;

import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.ModoPagamentoJDBC;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PagamentoJDBC;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FinanceiroRepositoryGateway implements FinanceiroDsGateways {

    private final ModoPagamentoJDBC modoPagamentoJDBC;
    private final PagamentoJDBC pagamentoJDBC;

    @Override
    public ModoPagamento saveModoPagamento(ModoPagamento modoPagamento) {
        if(modoPagamento.getId() == null)
            return modoPagamentoJDBC.insert(modoPagamento);
        else 
            return modoPagamentoJDBC.update(modoPagamento);
    }

    @Override
    public ModoPagamento findModoPagamentoPorIdPaciente(Long idPaciente) {
        return modoPagamentoJDBC.findByIdPaciente(idPaciente);
    }

    @Override
    public Pagamento savePagamento(Pagamento pagamento) {
        if(pagamento.getId() == null)
            return pagamentoJDBC.insert(pagamento);
        else 
            return pagamentoJDBC.update(pagamento);
    }

    @Override
    public Pagamento findById(Long id) {
        return pagamentoJDBC.findById(id);
    }

    @Override
    public Pagamento findByIdPaciente(Long idPaciente) {
        return pagamentoJDBC.findByIdPaciente(idPaciente);
    }

    @Override
    public void deletePagamento(Long id) {
        pagamentoJDBC.delete(id);
    }


    
}
