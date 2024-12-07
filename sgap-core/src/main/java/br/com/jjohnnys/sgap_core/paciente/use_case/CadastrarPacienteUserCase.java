package br.com.jjohnnys.sgap_core.paciente.use_case;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.repository.jdbc.PacienteRepository;

@Service
public class CadastrarPacienteUserCase {

    private PacienteRepository pacienteRepository;
    
    public CadastrarPacienteUserCase(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void execute(Paciente paciente) {

        pacienteRepository.insert(paciente);

    }

}
