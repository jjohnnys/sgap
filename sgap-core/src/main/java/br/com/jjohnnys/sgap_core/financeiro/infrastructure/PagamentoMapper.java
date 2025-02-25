package br.com.jjohnnys.sgap_core.financeiro.infrastructure;

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
            rs.getLong("idPaciente"),
            LocalDate.parse(rs.getString("data")),
            StatusPagamentoEnum.valueOf(rs.getString("status")));
        return modoPagamento;   
        
    }    
}
