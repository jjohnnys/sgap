package br.com.jjohnnys.sgap_core.financeiro.application.usecases;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@Service
public class FazerPagamentoUseCase {

    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;

    public void execute(PagamentoDTO pagamentoDTO) {
        Paciente paciente = pacienteDsGateway.findPacienteById(pagamentoDTO.idPaciente()).get();
        if(paciente == null) throw new DadosFinanceiroException("Paciente invalido para pagamento");
        if(!StatusAtendimentoEnum.ATIVO.equals(paciente.getStatus())) throw new DadosFinanceiroException("Não pode ser realizado pagamento de paciente não ATIVO");
        Pagamento pagamento = pagamentoDTO.criarPagamento();
        ModoPagamento modoPagamento = financeiroDsGateways.findModoPagamentoPorIdPaciente(pagamentoDTO.idPaciente());
        if(PlanoEnum.VOLUNTARIO.equals(modoPagamento.getPlano())) throw new DadosFinanceiroException("Paciente com plano voluntário não pode ser feito o pagamento");
        BigDecimal valorAPagar = modoPagamento.getValorAPagarNoMes();
        if(!(valorAPagar.compareTo(pagamento.getValor()) == 0))
        throw new DadosFinanceiroException(String.format("O valor a ser pago deve ser de %s", valorAPagar));
        pagamento.defineStatus(modoPagamento.getDiaDoMes());
        financeiroDsGateways.savePagamento(pagamento);
    }
}
