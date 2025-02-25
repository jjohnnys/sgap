package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;

public class ResponsavelMapper implements RowMapper<Responsavel> {

    @Override
    public Responsavel mapRow(ResultSet rs, int arg1) throws SQLException {
        Responsavel responsavel = new Responsavel(
            rs.getLong("id"),
            rs.getString("nome"),
            new CpfCnpj(rs.getString("cpf_cnpj"), FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString("fisica_juridica").charAt(0))),
            new Rg(rs.getString("rg")),
            FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString("fisica_juridica").charAt(0)),
            LocalDate.parse(rs.getString("data_nascimento")),
            rs.getString("profissao"),
            rs.getString("endereco"),
            rs.getString("observacao"),
            new Email(rs.getString("email")),
            rs.getString("telefones") != null ? Set.of(new Telefone(rs.getString("telefones"))) : null);
        return responsavel;




    }    
}
