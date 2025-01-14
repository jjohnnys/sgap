package br.com.jjohnnys.sgap_core.paciente.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;

@Service
public class ExcluirPacienteUserCase {

    private PacienteDsGateway pacienteDsGateway;
    
    public ExcluirPacienteUserCase(PacienteDsGateway pacienteDsGateway) {
        this.pacienteDsGateway = pacienteDsGateway;
    }

    public void execute(Long id) {
        pacienteDsGateway.findPacienteById(id).orElseThrow(() -> new DadosPacienteException("Paciente não encontrado"));
        pacienteDsGateway.excluiPaciente(id);
    }

}
