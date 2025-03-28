package br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;

public class PagamentoMapper implements RowMapper<Pagamento> {

    @Override
    public Pagamento mapRow(ResultSet rs, int arg1) throws SQLException {
        Pagamento modoPagamento = new Pagamento(
            rs.getLong("id"),
            null,
            LocalDate.parse(rs.getString("data")),
            rs.getBigDecimal("valor"),
            StatusPagamentoEnum.valueOf(rs.getString("status")));
        return modoPagamento;   
        
    }     
}
