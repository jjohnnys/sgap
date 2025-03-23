package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.financeiro.application.presenter.RelatorioPagamentoPresenter;

public class RelatorioPagamentoMapper implements RowMapper<RelatorioPagamentoPresenter> {

    @Override
    public RelatorioPagamentoPresenter mapRow(ResultSet rs, int arg1) throws SQLException {
        RelatorioPagamentoPresenter relatorioPagamentoPresenter = new RelatorioPagamentoPresenter(
            rs.getString("pac.nome"),
            rs.getString("pac.cpf_cnpj"),
            rs.getInt("mes"),
            rs.getInt("ano"),
            rs.getBigDecimal("pg.valor"),
            rs.getString("pg.status"),
            null);
        return relatorioPagamentoPresenter;
    }    
}
