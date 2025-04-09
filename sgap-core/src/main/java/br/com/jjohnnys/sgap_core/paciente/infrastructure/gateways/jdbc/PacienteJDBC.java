package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.EscolaridadeEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.GeneroEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;

@Repository
public class PacienteJDBC {

    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private ResponsavelJDBC responsavelJDBC;

    public Optional<Paciente> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM paciente WHERE id = ?").param(id).query(this::mapRow).optional();
    }

    public Optional<Paciente> findByNome(String nome) {
        return jdbcClient.sql("SELECT * FROM paciente WHERE nome = ?").param(nome).query(this::mapRow).optional();
    }

    public Paciente insert(Paciente paciente) {
        String sql = "INSERT INTO paciente (nome, cpf_cnpj, rg, fisica_juridica, data_nascimento, escolaridade, profissao, genero, endereco, status, observacao, dependente, email, telefones) values (:nome, :cpfCnpj, :rg, :fisicaJuridica, :dataNascimento, :escolaridade, :profissao, :genero, :endereco, :status, :observacao, :dependente,  :email, :telefones)";
        jdbcClient.sql(sql)
        .param("nome", paciente.getNome())
        .param("cpfCnpj", paciente.getCpfCnpj().getValor())
        .param("rg", paciente.getRg().getValor())
        .param("fisicaJuridica", paciente.getFisicaJuridica().getValor())
        .param("dataNascimento", paciente.getDataNascimento())
        .param("escolaridade", paciente.getEscolaridade().getValor())
        .param("profissao", paciente.getProfissao())
        .param("genero", paciente.getGenero().getValor())
        .param("endereco", paciente.getEndereco())
        .param("status", paciente.getStatus().getValor())
        .param("observacao", paciente.getObservacao())
        .param("dependente", paciente.isDependente())
        .param("email", paciente.getEmail().getValor())
        .param("telefones", String.join(",", paciente.getTelefones().stream().map(Telefone::getValor).toList())).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado).get();
    }

    public Paciente update(Paciente paciente) {
        String sql = "UPDATE paciente SET , nome = :nome, cpf_cnpj = :cpfCnpj, rg = :rg, fisica_juridica = :fisicaJuridica, data_nascimento = :dataNascimento, escolaridade = :escolaridade, profissao = :profissao, genero = :genero, endereco = :endereco, observacao = :observacao WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id", paciente.getId())
            .param("nome", paciente.getNome())
            .param("cpfCnpj", paciente.getCpfCnpj().getValor())
            .param("rg", paciente.getRg().getValor())
            .param("fisicaJuridica", paciente.getFisicaJuridica().getValor())
            .param("dataNascimento", paciente.getDataNascimento())
            .param("escolaridade", paciente.getEscolaridade().getValor())
            .param("profissao", paciente.getProfissao())
            .param("genero", paciente.getGenero().getValor())
            .param("endereco", paciente.getEndereco())
            .param("status", paciente.getStatus().getValor())
            .param("observacao", paciente.getObservacao())
            .param("dependente", paciente.isDependente())
            .param("email", paciente.getEmail())
            .param("telefones", paciente.getTelefones() != null ? paciente.getTelefones().stream().map(Telefone::getValor).toList() : null).update();
        return findById(paciente.getId()).get();
    }

    public int delete(Long id) {
        return jdbcClient.sql("DELETE FROM paciente  WHERE id = ?").param(id).update();
    }
    
    public int deleteAll() {
        return jdbcClient.sql("DELETE FROM paciente").update();
    }
    
    public int updateStatus(Long id, String status) {
        return jdbcClient.sql("UPDATE paciente SET status = ? WHERE id = ?").param(status).param(id).update();
    }

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
            responsavelJDBC.findByPacienteId(rs.getLong("id")),
            new Email(rs.getString("email")),
            rs.getString("telefones") != null ? Set.of(new Telefone(rs.getString("telefones"))): null);
        return paciente;   
        
    }

    
}
