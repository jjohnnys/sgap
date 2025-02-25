package br.com.jjohnnys.sgap_core.paciente.application.usecases;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.paciente.application.dto.AlteraStatusDto;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;

@Service
public class AlterarStatusPacienteUserCase {

    private PacienteDsGateway pacienteDsGateway;
    
    public AlterarStatusPacienteUserCase(PacienteDsGateway pacienteDsGateway) {
        this.pacienteDsGateway = pacienteDsGateway;
    }

    public void execute(AlteraStatusDto alteraStatusDto) {

        Paciente paciente = pacienteDsGateway.findPacienteById(alteraStatusDto.idPaciente()).orElseThrow(() ->  
            new DadosPacienteException(String.format("Id do paciente %s invalido", alteraStatusDto.idPaciente())));
        StatusAtendimentoEnum statusAtendimento = StatusAtendimentoEnum.getStatusAtendimentoEnumPorValor(alteraStatusDto.novoStatus());
        
       if(!paciente.validaMudancaStatus(statusAtendimento)) 
        throw new DadosPacienteException(
            String.format("Relação de alteração de status de %s para %s é invalida", paciente.getStatus().getValor(), alteraStatusDto.novoStatus()));    

        pacienteDsGateway.updateStatus(paciente.getId(), statusAtendimento.getValor());
                
    }

}
