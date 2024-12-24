package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;

public class ResponsavelMapper implements RowMapper<Responsavel> {

    @Override
    public Responsavel mapRow(ResultSet rs, int arg1) throws SQLException {
        Responsavel responsavel = new Responsavel(
            rs.getLong(1),
            rs.getString(2),
            new CpfCnpj(rs.getString(3), FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString(5).charAt(0))),
            new Rg(rs.getString(4)),
            FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString(5).charAt(0)),
            LocalDate.parse(rs.getString(6)),
            rs.getString(9),
            rs.getString(10),
            rs.getString(12));
        return responsavel;
    }    
}
