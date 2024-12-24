package br.com.jjohnnys.sgap_core.paciente.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

public class PacienteExtractor implements ResultSetExtractor<Paciente> {

    @Override
    public Paciente extractData(ResultSet rs) throws SQLException, DataAccessException {
        rs.next();
        return new PacienteMapper().mapRow(rs, 0);
    }
    
}
