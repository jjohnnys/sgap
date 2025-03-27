package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.RelatorioPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;

@Repository
public class PagamentoJDBC {    

    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private PlanoAtendimentoJDBC atendimentoJDBC;

    private String  QUERY_FIND_BY_ID_PACIENTE = "SELECT * FROM pagamentos pg, plano_atendimento pa WHERE pa.id_paciente = ? and pg.id_plano_atendimento = pa.id";    

    public Pagamento findById(Long id) {
        return jdbcClient.sql("SELECT * FROM pagamentos WHERE id = ?").param(id).query(this::mapRow).single();
    }

    public List<Pagamento> findByIdPaciente(Long idPaciente) {
        return jdbcClient.sql(QUERY_FIND_BY_ID_PACIENTE).param(idPaciente).query(this::mapRow).stream().toList();
    }

    public Pagamento insert(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos (id_plano_atendimento, data, valor, status) values (:id_plano_atendimento, :data, :valor, :status)";
        jdbcClient.sql(sql)
        .param("id_plano_atendimento", pagamento.getPlanoAtendimento().getId())
        .param("data", pagamento.getData())
        .param("valor", pagamento.getValor())
        .param("status", pagamento.getStatus().name()).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado);
    }

    public Pagamento update(Pagamento pagamento) {
        String sql = "UPDATE pagamentos SET id_plano_atendimento = :id_plano_atendimento, data = :data, valor, status = :status WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id_plano_atendimento", pagamento.getPlanoAtendimento())
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

    public Pagamento mapRow(ResultSet rs, int arg1) throws SQLException {
        Pagamento modoPagamento = new Pagamento(
            rs.getLong("id"),
            atendimentoJDBC.findById(rs.getLong("id_plano_atendimento")),
            LocalDate.parse(rs.getString("data")),
            rs.getBigDecimal("valor"),
            StatusPagamentoEnum.valueOf(rs.getString("status")));
        return modoPagamento;   
        
    }
}
