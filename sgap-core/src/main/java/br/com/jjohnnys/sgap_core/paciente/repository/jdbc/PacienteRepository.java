package br.com.jjohnnys.sgap_core.paciente.repository.jdbc;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

public interface PacienteRepository {

    Paciente findById(Long id);
    Paciente findByNome(String nome);
    Paciente insert(Paciente paciente);
    Paciente update(Paciente paciente);


    
}
