package br.com.jjohnnys.sgap_core.paciente.repository.jdbc;

import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;

public interface ResponsavelRepository {

    Responsavel findById(Long id);
    Responsavel findByNome(String nome);
    Responsavel insert(Responsavel responsavel);
    Responsavel update(Responsavel responsavel);


    
}
