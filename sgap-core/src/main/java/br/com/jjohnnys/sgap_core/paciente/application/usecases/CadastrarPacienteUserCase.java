package br.com.jjohnnys.sgap_core.paciente.application.usecases;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;

@Service
public class CadastrarPacienteUserCase {

    private PacienteDsGateway pacienteDsGateway;
    
    public CadastrarPacienteUserCase(PacienteDsGateway pacienteDsGateway) {
        this.pacienteDsGateway = pacienteDsGateway;
    }

    public void execute(PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteDTO.criarPaciente();
        Paciente pacienteSalvo = pacienteDsGateway.savePaciente(paciente);
        if(!paciente.validaDependenteResponsaveis())
                throw new DadosPacienteException("Paciente dependente deve ter dados do responsavel");
        if(paciente.isDependente())        
            paciente.getResponsaveis().forEach(responsavel -> pacienteDsGateway.saveResponsavel(responsavel, pacienteSalvo));
        
    }

}
