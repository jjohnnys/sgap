package br.com.jjohnnys.sgap_core.paciente.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.paciente.application.enums.DepenRespEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;

public class PacienteMapper implements RowMapper<Paciente> {

    @Override
    public Paciente mapRow(ResultSet rs, int arg1) throws SQLException {
        Paciente paciente = new Paciente(
            rs.getLong(1),
            rs.getString(2),
            new CpfCnpj(rs.getString(3), FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString(5).charAt(0))),
            rs.getString(4),
            FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString(5).charAt(0)),
            LocalDate.parse(rs.getString(6)),
            rs.getString(7),
            rs.getString(8),
            rs.getString(9),
            rs.getString(10),
            rs.getString(11),
            rs.getString(12),
            DepenRespEnum.getDepenRespEnumPorValor(rs.getString(13)));
        return paciente;    
    }


    
}
