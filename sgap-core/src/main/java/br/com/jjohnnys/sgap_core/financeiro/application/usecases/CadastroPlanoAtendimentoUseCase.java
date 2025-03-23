package br.com.jjohnnys.sgap_core.financeiro.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PlanoAtendimentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastroPlanoAtendimentoUseCase {

    private final FinanceiroDsGateways financeiroDsGateways;
    private final PacienteDsGateway pacienteDsGateway;

    public void execute(PlanoAtendimentoDTO planoAtendimentoDTO) {
        Optional<Paciente> paciente = pacienteDsGateway.findPacienteById(planoAtendimentoDTO.idPaciente());
        if(paciente.isEmpty()) throw new DadosFinanceiroException("Paciente invalido");
        PlanoAtendimento planoAtendimento = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(planoAtendimentoDTO.idPaciente());
        if(planoAtendimento != null && !planoAtendimento.getPaciente().getId().equals(planoAtendimentoDTO.idPaciente()))
            throw new DadosFinanceiroException("Relação Plano atendimento e paciente inválido"); 
        else 
            planoAtendimento = new PlanoAtendimento(planoAtendimentoDTO.id(), paciente.get(), PlanoEnum.getPlanoEnum(planoAtendimentoDTO.plano()), planoAtendimentoDTO.valor(), planoAtendimentoDTO.diaDoMes());
        planoAtendimento.validaCamposNulos();
        planoAtendimento.validaValor();
        financeiroDsGateways.savePlanoAtendimento(planoAtendimento);
    }
    
}
