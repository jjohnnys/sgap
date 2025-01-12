package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import br.com.jjohnnys.sgap_core.paciente.application.enums.EscolaridadeEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.GeneroEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;

public class PacienteMapper implements RowMapper<Paciente> {

    @Override
    public Paciente mapRow(ResultSet rs, int arg1) throws SQLException {
        Paciente paciente = new Paciente(
            rs.getLong("id"),
            rs.getString("nome"),
            new CpfCnpj(rs.getString("cpf_cnpj"), FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString("fisica_juridica").charAt(0))),
            new Rg(rs.getString("rg")),
            FisicaJuridicaEnum.getFisicaJuridicaEnumPorValor(rs.getString("fisica_juridica").charAt(0)),
            LocalDate.parse(rs.getString("data_nascimento")),
            EscolaridadeEnum.getEscolaridadeEnumPorValor(rs.getString("escolaridade")),
            GeneroEnum.getGeneroEnumPorValor(rs.getString("genero")),
            rs.getString("profissao"),
            rs.getString("endereco"),
            StatusAtendimentoEnum.getStatusAtendimentoEnumPorValor(rs.getString("status")),
            rs.getString("observacao"),
            rs.getBoolean("dependente"),
            null,
            new Email(rs.getString("email")),
            rs.getString("telefones") != null ? Set.of(new Telefone(rs.getString("telefones"))): null);
        return paciente;   
        
    }


    
}
