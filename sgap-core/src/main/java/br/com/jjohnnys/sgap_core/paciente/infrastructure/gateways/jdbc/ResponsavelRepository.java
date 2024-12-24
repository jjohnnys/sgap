package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;

public interface ResponsavelRepository {

    Responsavel findById(Long id);
    Responsavel findByNome(String nome);
    Responsavel findByPacienteId(Long idPaciente);
    Responsavel insert(Responsavel responsavel);
    Responsavel update(Responsavel responsavel);


    
}
