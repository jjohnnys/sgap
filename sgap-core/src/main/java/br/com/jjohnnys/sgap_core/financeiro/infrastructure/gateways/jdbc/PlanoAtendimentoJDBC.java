package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;

@Repository
public class PlanoAtendimentoJDBC {

    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private PacienteJDBC pacienteJDBC;

    public PlanoAtendimento findById(Long id) {
        return jdbcClient.sql("SELECT * FROM plano_atendimento WHERE id = ?").param(id).query(this::mapRow).single();
    }

    public PlanoAtendimento findByIdPaciente(Long nome) {
        return jdbcClient.sql("SELECT * FROM plano_atendimento WHERE id_paciente = ?")
                      .param(nome)
                      .query(this::mapRow)
                      .stream().findFirst().orElse(null);
        
    }

    public PlanoAtendimento insert(PlanoAtendimento modoPagamento) {
        String sql = "INSERT INTO plano_atendimento (id_paciente, plano, valor, dia_pagamento) values (:id_paciente, :plano, :valor, :dia_pagamento)";
        jdbcClient.sql(sql)
        .param("id_paciente", modoPagamento.getPaciente().getId())
        .param("plano", modoPagamento.getPlano().name())
        .param("valor", modoPagamento.getValorPorConsulta())
        .param("dia_pagamento", modoPagamento.getDiaPagamento()).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado);
    }

    public PlanoAtendimento update(PlanoAtendimento modoPagamento) {
        String sql = "UPDATE plano_atendimento SET id_paciente = :id_paciente, plano = :plano, valor = :valor, dia_pagamento = :dia_pagamento WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id_paciente", modoPagamento.getPaciente().getId())
            .param("plano", modoPagamento.getPlano().name())
            .param("valor", modoPagamento.getValorPorConsulta())
            .param("dia_pagamento", modoPagamento.getDiaPagamento())
            .param("id", modoPagamento.getId()).update();
        return findById(modoPagamento.getId());
    }

    public int delete(Long id) {
        return jdbcClient.sql("DELETE FROM plano_atendimento  WHERE id = ?").param(id).update();
    }
    
    public int deleteAll() {
        return jdbcClient.sql("DELETE FROM plano_atendimento").update();
    }

    public PlanoAtendimento mapRow(ResultSet rs, int arg1) throws SQLException {
        PlanoAtendimento modoPagamento = new PlanoAtendimento(
            rs.getLong("id"),
            pacienteJDBC.findById(rs.getLong("id_paciente")).get(),
            PlanoEnum.valueOf(rs.getString("plano")),
            rs.getBigDecimal("valor"),
            rs.getInt("dia_pagamento"));
        return modoPagamento;   
        
    }  

    
}
