package br.com.jjohnnys.sgap_core.paciente.application.gateways;

import java.util.Optional;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;

public interface PacienteDsGateway {

    Optional<Paciente> findPacienteById(Long id);
    Optional<Paciente> findPacienteByNome(String nome);
    Optional<Responsavel> findResponsavelById(Long id);
    Optional<Responsavel> findResponsavelByNome(String nome);
    Paciente savePaciente(Paciente paciente);
    void excluiPaciente(Long id);
    Responsavel saveResponsavel(Responsavel responsavel, Paciente paciente);
    void excluiResponsavel(Long id);
    
}
