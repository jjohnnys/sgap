package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;

@Repository
public class PagamentoJDBC {

    @Autowired
    private JdbcClient jdbcClient;

    public Pagamento findById(Long id) {
        return jdbcClient.sql("SELECT * FROM pagamentos WHERE id = ?").param(id).query(new PagamentoMapper()).single();
    }

    public Pagamento findByIdPaciente(Long idPaciente) {
        return jdbcClient.sql("SELECT * FROM pagamentos WHERE id_paciente = ?").param(idPaciente).query(new PagamentoMapper()).single();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Pagamento> find(Long idPaciente, LocalDate dataInicio, LocalDate dataFim, StatusPagamentoEnum status) {

        StringBuilder query = new StringBuilder();
        List parans = new ArrayList<>();
        query.append("SELECT * FROM pagamentos WHERE 0 = 0 ");
        if(idPaciente != null) {
            query.append(" AND id_paciente = ? ");
            parans.add(idPaciente);
        }   
        if(status != null) {
            query.append(" AND status = ? ");
            parans.add(status.name());
        } 
        if(dataInicio != null) {
            query.append(" AND data  >= ? ");
            parans.add(dataInicio);
        } 
        if(dataFim != null) {
            query.append(" AND data  <= ? ");
            parans.add(dataFim);
        }
        return jdbcClient.sql(query.toString()).params(parans).query(new PagamentoMapper()).stream().toList();
    }

    public Pagamento insert(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos (id_paciente, data, valor, status) values (:id_paciente, :data, :valor, :status)";
        jdbcClient.sql(sql)
        .param("id_paciente", pagamento.getIdPaciente())
        .param("data", pagamento.getData())
        .param("valor", pagamento.getValor())
        .param("status", pagamento.getStatus().name()).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado);
    }

    public Pagamento update(Pagamento pagamento) {
        String sql = "UPDATE pagamentos SET , id_paciente = :id_paciente, data = :data, valor, status = :status WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id_paciente", pagamento.getIdPaciente())
            .param("data", pagamento.getData())
            .param("valor", pagamento.getValor())
            .param("status", pagamento.getStatus().name()).update();
        return findById(pagamento.getId());
    }

    public int delete(Long id) {
        return jdbcClient.sql("DELETE FROM pagamentos  WHERE id = ?").param(id).update();
    }
    
    public int deleteAll() {
        return jdbcClient.sql("DELETE FROM pagamentos").update();
    }    
}
