package br.com.jjohnnys.sgap_core.financeiro.infrastructure;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;

@Repository
public class PagamentoJDBC {

    @Autowired
    private JdbcClient jdbcClient;

    public Optional<Pagamento> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM pagamentos WHERE id = ?").param(id).query(new PagamentoMapper()).optional();
    }

    public Optional<Pagamento> findByIdPaciente(String nome) {
        return jdbcClient.sql("SELECT * FROM pagamentos WHERE id_paciente = ?").param(nome).query(new PagamentoMapper()).optional();
    }

    public Pagamento insert(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos (id, id_paciente, plano, valor, data) values (:id, :id_paciente, :plano, :valor, :data)";
        jdbcClient.sql(sql)
        .param("nome", pagamento.getId())
        .param("id_paciente", pagamento.getIdPaciente())
        .param("data", pagamento.getData())
        .param("status", pagamento.getStatus().name());
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado).get();
    }

    public Pagamento update(Pagamento pagamento) {
        String sql = "UPDATE pagamentos SET , id_paciente = :id_paciente, plano = :plano, valor = :valor, data = :data WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id_paciente", pagamento.getIdPaciente())
            .param("data", pagamento.getData())
            .param("status", pagamento.getStatus().name()).update();
        return findById(pagamento.getId()).get();
    }

    public int delete(Long id) {
        return jdbcClient.sql("DELETE FROM pagamentos  WHERE id = ?").param(id).update();
    }
    
    public int deleteAll() {
        return jdbcClient.sql("DELETE FROM pagamentos").update();
    }    
}
