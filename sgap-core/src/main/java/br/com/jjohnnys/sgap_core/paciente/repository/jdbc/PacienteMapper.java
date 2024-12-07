package br.com.jjohnnys.sgap_core.paciente.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

public class PacienteMapper implements RowMapper<Paciente> {

    @Override
    public Paciente mapRow(ResultSet rs, int arg1) throws SQLException {
        Paciente paciente = new Paciente(
            rs.getLong(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            LocalDate.parse(rs.getString(5)),
            rs.getString(6),
            rs.getString(7),
            rs.getString(8),
            rs.getString(9),
            rs.getString(10));
        return paciente;    
    }


    
}
