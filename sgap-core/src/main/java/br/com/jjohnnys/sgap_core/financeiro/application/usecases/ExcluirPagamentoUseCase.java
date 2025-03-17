package br.com.jjohnnys.sgap_core.financeiro.application.usecases;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

@Service
public class ExcluirPagamentoUseCase {

    private PacienteDsGateway pacienteDsGateway;
    private FinanceiroDsGateways financeiroDsGateways;

    public void execute(PagamentoDTO pagamentoDTO) {
        Paciente paciente = pacienteDsGateway.findPacienteById(pagamentoDTO.idPaciente()).get();
        if(paciente == null) throw new DadosFinanceiroException("Paciente invalido para pagamento");
        Pagamento pagamento = pagamentoDTO.criarPagamento();        
        financeiroDsGateways.deletePagamento(pagamento.getId());
    }
    
}
