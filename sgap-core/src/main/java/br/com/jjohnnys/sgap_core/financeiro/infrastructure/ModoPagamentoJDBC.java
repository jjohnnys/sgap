package br.com.jjohnnys.sgap_core.financeiro.infrastructure;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;

@Repository
public class ModoPagamentoJDBC {

    @Autowired
    private JdbcClient jdbcClient;

    public Optional<ModoPagamento> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM modo_pagamento WHERE id = ?").param(id).query(new ModoPagamentoMapper()).optional();
    }

    public Optional<ModoPagamento> findByIdPaciente(String nome) {
        return jdbcClient.sql("SELECT * FROM modo_pagamento WHERE id_paciente = ?").param(nome).query(new ModoPagamentoMapper()).optional();
    }

    public ModoPagamento insert(ModoPagamento modoPagamento) {
        String sql = "INSERT INTO modo_pagamento (id, id_paciente, plano, valor, data) values (:id, :id_paciente, :plano, :valor, :data)";
        jdbcClient.sql(sql)
        .param("nome", modoPagamento.getId())
        .param("id_paciente", modoPagamento.getIdPaciente())
        .param("plano", modoPagamento.getPlano().name())
        .param("valor", modoPagamento.getValor().getValor())
        .param("data", modoPagamento.getData()).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado).get();
    }

    public ModoPagamento update(ModoPagamento modoPagamento) {
        String sql = "UPDATE modo_pagamento SET , id_paciente = :id_paciente, plano = :plano, valor = :valor, data = :data WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id_paciente", modoPagamento.getIdPaciente())
            .param("plano", modoPagamento.getPlano().name())
            .param("valor", modoPagamento.getValor().getValor())
            .param("data", modoPagamento.getData()).update();
        return findById(modoPagamento.getId()).get();
    }

    public int delete(Long id) {
        return jdbcClient.sql("DELETE FROM modo_pagamento  WHERE id = ?").param(id).update();
    }
    
    public int deleteAll() {
        return jdbcClient.sql("DELETE FROM modo_pagamento").update();
    }

    
}
