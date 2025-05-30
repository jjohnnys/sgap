package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.RelatorioPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.presenter.PagamentoPresenter;

@Repository
public class RelatorioPagamentoJDBC {

    @Autowired
    private JdbcClient jdbcClient;

    private String  QUERY_FIND_BY_NOME_PACIENTE = "SELECT pc.nome as nome,  pc.cpf_cnpj as cpf_cnpj, pg.data as data, pg.valor as valor, pg.status as status FROM pagamentos pg, plano_atendimento pa, paciente pc WHERE pc.id = pa.id_paciente and pg.id_plano_atendimento = pa.id";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<PagamentoPresenter.DadosPagamentoPaciente> findRelatorio(RelatorioPagamentoDTO relatorioPagamentoDTO ) {

        StringBuilder query = new StringBuilder();
        List parans = new ArrayList<>();
        query.append(QUERY_FIND_BY_NOME_PACIENTE);
        if(relatorioPagamentoDTO.getNomePaciente() != null) {
            query.append(" AND pc.nome = ? ");
            parans.add(relatorioPagamentoDTO.getNomePaciente());
        }   
        if(relatorioPagamentoDTO.getStatusPagamento() != null) {
            query.append(" AND pg.status = ? ");
            parans.add(relatorioPagamentoDTO.getStatusPagamento());
        } 
        if(relatorioPagamentoDTO.getMesInicio() != null) {
            query.append(" AND Extract(month from pg.data)  >= ? ");
            parans.add(relatorioPagamentoDTO.getMesInicio());
        } 
        if(relatorioPagamentoDTO.getMesFim() != null) {
            query.append(" AND Extract(month from pg.data)  <= ? ");
            parans.add(relatorioPagamentoDTO.getMesFim());
        }
        if(relatorioPagamentoDTO.getAnoInicio() != null) {
            query.append(" AND Extract(year from pg.data)  >= ? ");
            parans.add(relatorioPagamentoDTO.getAnoInicio());
        } 
        if(relatorioPagamentoDTO.getAnoFim() != null) {
            query.append(" AND Extract(year from pg.data)  <= ? ");
            parans.add(relatorioPagamentoDTO.getAnoFim());
        }        
        if(relatorioPagamentoDTO.getStatusPagamento() != null) {
            query.append(" AND pg.status  = ? ");
            parans.add(relatorioPagamentoDTO.getStatusPagamento());
        }
        return jdbcClient.sql(query.toString()).params(parans).query(this::mapRow).stream().toList();
    }

    public PagamentoPresenter.DadosPagamentoPaciente mapRow(ResultSet rs, int arg1) throws SQLException {
        PagamentoPresenter pagamentoPresenter = new PagamentoPresenter();
        PagamentoPresenter.DadosPagamentoPaciente dadosPagamentoPaciente = pagamentoPresenter.new DadosPagamentoPaciente(
            rs.getString("nome"), 
            rs.getString("cpf_cnpj"), 
            rs.getDate("data"), 
            rs.getBigDecimal("valor"), 
            rs.getString("status"));
        return dadosPagamentoPaciente;
        
    }
}
