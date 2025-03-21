package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;

@Repository
public class ModoPagamentoJDBC {

    @Autowired
    private JdbcClient jdbcClient;

    public ModoPagamento findById(Long id) {
        return jdbcClient.sql("SELECT * FROM modo_pagamento WHERE id = ?").param(id).query(new ModoPagamentoMapper()).single();
    }

    public ModoPagamento findByIdPaciente(Long nome) {
        return jdbcClient.sql("SELECT * FROM modo_pagamento WHERE id_paciente = ?").param(nome).query(new ModoPagamentoMapper()).single();
    }

    public ModoPagamento insert(ModoPagamento modoPagamento) {
        String sql = "INSERT INTO modo_pagamento (id_paciente, plano, valor, dia_do_mes) values (:id_paciente, :plano, :valor, :dia_do_mes)";
        jdbcClient.sql(sql)
        .param("id_paciente", modoPagamento.getIdPaciente())
        .param("plano", modoPagamento.getPlano().name())
        .param("valor", modoPagamento.getValorPorConsulta())
        .param("dia_do_mes", modoPagamento.getDiaDoMes()).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado);
    }

    public ModoPagamento update(ModoPagamento modoPagamento) {
        String sql = "UPDATE modo_pagamento SET id_paciente = :id_paciente, plano = :plano, valor = :valor, dia_do_mes = :dia_do_mes WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id_paciente", modoPagamento.getIdPaciente())
            .param("plano", modoPagamento.getPlano().name())
            .param("valor", modoPagamento.getValorPorConsulta())
            .param("dia_do_mes", modoPagamento.getDiaDoMes())
            .param("id", modoPagamento.getId()).update();
        return findById(modoPagamento.getId());
    }

    public int delete(Long id) {
        return jdbcClient.sql("DELETE FROM modo_pagamento  WHERE id = ?").param(id).update();
    }
    
    public int deleteAll() {
        return jdbcClient.sql("DELETE FROM modo_pagamento").update();
    }

    
}
