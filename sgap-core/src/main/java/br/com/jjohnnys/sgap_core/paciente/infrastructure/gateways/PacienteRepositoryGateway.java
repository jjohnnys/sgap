package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteResponsavelJDBC;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.ResponsavelJDBC;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PacienteRepositoryGateway implements PacienteDsGateway {

    private PacienteJDBC pacienteJDBC;
    private PacienteResponsavelJDBC pacienteResponsavelJDBC;
    private ResponsavelJDBC responsavelJDBC;

    @Override
    public Paciente findById(Long id) {
        return pacienteJDBC.findById(id);
    }

    @Override
    public Paciente findByNome(String nome) {
        Paciente paciente = pacienteJDBC.findByNome(nome);
        if(paciente == null) return null;
        List<Responsavel> responsaveis = responsavelJDBC.findByPacienteId(paciente.getId());
        responsaveis.stream().forEach(paciente::adicionaResponsavel);
        return paciente;
    }

    @Override
    public Paciente savePaciente(Paciente paciente) {
        Paciente pacienteSalvo = null;    
        if (paciente.getId() == null) 
            pacienteSalvo = pacienteJDBC.insert(paciente);
        else pacienteSalvo = pacienteJDBC.update(paciente);
        return pacienteSalvo;
    }

    @Override
    public Responsavel saveResponsavel(Responsavel responsavel, Paciente paciente) {
        Responsavel responsavelSalvo = null;    
        if (responsavel.getId() == null) 
            responsavelSalvo = responsavelJDBC.insert(responsavel);
        else responsavelSalvo = responsavelJDBC.update(responsavel);
        pacienteResponsavelJDBC.insert(paciente.getId(), responsavelSalvo.getId());
        return responsavelSalvo;
    }

    
    
}
