package br.com.jjohnnys.sgap_core.financeiro.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.value_object.Valor;

public class ModoPagamentoMapper implements RowMapper<ModoPagamento> {

    @Override
    public ModoPagamento mapRow(ResultSet rs, int arg1) throws SQLException {
        ModoPagamento modoPagamento = new ModoPagamento(
            rs.getLong("id"),
            rs.getLong("idPaciente"),
            PlanoEnum.valueOf(rs.getString("plano")),
            new Valor(rs.getFloat("valor")),
            LocalDate.parse(rs.getString("data_nascimento")));
        return modoPagamento;   
        
    }    
}
