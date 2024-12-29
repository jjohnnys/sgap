package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class PacienteResponsavelJDBC {

    @Autowired
    private JdbcClient jdbcClient;

    public void insert(Long idPaciente, Long idResponsavel) {
        String sql = "INSERT INTO paciente_responsavel (id_paciente, id_responsavel) values (:id_paciente, :id_responsavel)";
        jdbcClient.sql(sql)
        .param("id_paciente", idPaciente)
        .param("id_responsavel", idResponsavel).update();
    }
    
}
