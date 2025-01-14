package br.com.jjohnnys.sgap_core.paciente.application.usecases;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;

@Service
public class ExcluirResponsavelUserCase {

    private PacienteDsGateway pacienteDsGateway;
    
    public ExcluirResponsavelUserCase(PacienteDsGateway pacienteDsGateway) {
        this.pacienteDsGateway = pacienteDsGateway;
    }

    public void execute(Long id) {
        pacienteDsGateway.findResponsavelById(id).orElseThrow(() -> new DadosPacienteException("Responsavel não encontrado"));
        pacienteDsGateway.excluiResponsavel(id);
    }

}
