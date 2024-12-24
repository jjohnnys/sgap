package br.com.jjohnnys.sgap_core.paciente.application.gateways;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;

public interface PacienteDsGateway {

    Paciente findById(Long id);
    Paciente findByNome(String nome);
    Paciente savePaciente(Paciente paciente);
    Responsavel saveResponsavel(Responsavel responsavel, Paciente paciente);
    
}
