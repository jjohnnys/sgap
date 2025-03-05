package br.com.jjohnnys.sgap_core.financeiro.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.ModoPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastrarModoPagamentoUseCase {

    private final FinanceiroDsGateways financeiroDsGateways;
    private final PacienteDsGateway pacienteDsGateway;

    public void execute(ModoPagamentoDTO modoPagamentoDTO) {
        Optional<Paciente> paciente = pacienteDsGateway.findPacienteById(modoPagamentoDTO.idPaciente());
        if(paciente.isEmpty()) throw new DadosFinanceiroException("Paciente invalido");        
        ModoPagamento modoPagamento = modoPagamentoDTO.criarModoPagamento();
        modoPagamento.validaCamposNulos();
        modoPagamento.validaValor();
        financeiroDsGateways.saveModoPagamento(modoPagamentoDTO.criarModoPagamento());
    }
    
}
