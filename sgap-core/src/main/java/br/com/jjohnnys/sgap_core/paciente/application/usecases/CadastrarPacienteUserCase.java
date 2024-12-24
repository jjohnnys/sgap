package br.com.jjohnnys.sgap_core.paciente.application.usecases;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

@Service
public class CadastrarPacienteUserCase {

    private PacienteDsGateway pacienteDsGateway;
    
    public CadastrarPacienteUserCase(PacienteDsGateway pacienteDsGateway) {
        this.pacienteDsGateway = pacienteDsGateway;
    }

    public void execute(PacienteDTO pacienteDTO) {

        Paciente paciente = pacienteDTO.criarPaciente();

        pacienteDsGateway.savePaciente(paciente);

        

        

    }

}
