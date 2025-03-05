package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;

public class ModoPagamentoMapper implements RowMapper<ModoPagamento> {

    @Override
    public ModoPagamento mapRow(ResultSet rs, int arg1) throws SQLException {
        ModoPagamento modoPagamento = new ModoPagamento(
            rs.getLong("id"),
            rs.getLong("id_paciente"),
            PlanoEnum.valueOf(rs.getString("plano")),
            rs.getBigDecimal("valor"),
            rs.getInt("dia_do_mes"));
        return modoPagamento;   
        
    }    
}
