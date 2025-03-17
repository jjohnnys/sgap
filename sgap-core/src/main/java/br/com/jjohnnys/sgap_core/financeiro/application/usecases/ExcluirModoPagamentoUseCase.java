package br.com.jjohnnys.sgap_core.financeiro.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.ModoPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcluirModoPagamentoUseCase {

    private final FinanceiroDsGateways financeiroDsGateways;

    public void execute(ModoPagamentoDTO modoPagamentoDTO) {
        ModoPagamento modoPagamento = financeiroDsGateways.findModoPagamentoPorIdPaciente(modoPagamentoDTO.idPaciente());
        if(modoPagamento == null || !modoPagamento.getId().equals(modoPagamentoDTO.id()))
            throw new DadosFinanceiroException("Modo pagamento invalido");        
        ModoPagamento novoModoPagamento = modoPagamentoDTO.criarModoPagamento();
        novoModoPagamento.validaCamposNulos();
        novoModoPagamento.validaValor();
        financeiroDsGateways.saveModoPagamento(novoModoPagamento);
    }
    
}
