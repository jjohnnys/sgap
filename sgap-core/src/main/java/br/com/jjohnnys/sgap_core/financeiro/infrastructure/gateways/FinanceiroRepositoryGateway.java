package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PagamentoJDBC;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PlanoAtendimentoJDBC;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FinanceiroRepositoryGateway implements FinanceiroDsGateways {

    private final PlanoAtendimentoJDBC planoAtendimentoJDBC;
    private final PagamentoJDBC pagamentoJDBC;

    @Override
    public PlanoAtendimento savePlanoAtendimento(PlanoAtendimento planoAtendimento) {
        if(planoAtendimento.getId() == null)
            return planoAtendimentoJDBC.insert(planoAtendimento);
        else 
            return planoAtendimentoJDBC.update(planoAtendimento);
    }

    @Override
    public PlanoAtendimento findPlanoAtendimentoPorIdPaciente(Long idPaciente) {
        return planoAtendimentoJDBC.findByIdPaciente(idPaciente);
    }

    @Override
    public Pagamento savePagamento(Pagamento pagamento) {        
        return pagamentoJDBC.insert(pagamento);
    }

    @Override
    public Pagamento findPagamentoById(Long id) {
        return pagamentoJDBC.findById(id);
    }

    @Override
    public List<Pagamento> findByIdPaciente(Long idPaciente) {
        return pagamentoJDBC.findByIdPaciente(idPaciente);
    }

    @Override
    public void deletePagamento(Long id) {
        pagamentoJDBC.delete(id);
    }


    
}
