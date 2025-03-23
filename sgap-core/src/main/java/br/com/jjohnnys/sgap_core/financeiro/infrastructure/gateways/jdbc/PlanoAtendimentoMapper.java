package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;

public class PlanoAtendimentoMapper implements RowMapper<PlanoAtendimento> {



    @Override
    public PlanoAtendimento mapRow(ResultSet rs, int arg1) throws SQLException {
        PlanoAtendimento modoPagamento = new PlanoAtendimento(
            rs.getLong("id"),
            null,
            PlanoEnum.valueOf(rs.getString("plano")),
            rs.getBigDecimal("valor"),
            rs.getInt("dia_pagamento"));
        return modoPagamento;   
        
    }    
}
