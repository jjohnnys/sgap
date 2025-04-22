package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ResponsavelJDBC {
    
    private JdbcClient jdbcClient;

    public Optional<Responsavel> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM responsavel WHERE id = ?").param(id).query(this::mapRow).optional();
    }

    public Optional<Responsavel> findByNome(String nome) {
        return jdbcClient.sql("SELECT * FROM responsavel WHERE nome = ?").param(nome).query(this::mapRow).optional();
    }
    
    public Set<Responsavel> findByPacienteId(Long idPaciente) {
        String sql = "select r.id, r.nome, r.cpf_cnpj, r.rg, r.fisica_juridica, r.data_nascimento, r.profissao, r.endereco, r.observacao, r.email, r.telefones " + 
                        "from responsavel r left outer join paciente_responsavel pr on r.id = pr.id_responsavel and pr.id_paciente = ?";
        return jdbcClient.sql(sql).param(idPaciente).query(this::mapRow).list().stream().collect(Collectors.toSet());
    }

    public Responsavel insert(Responsavel responsavel) {
        String sql = "INSERT INTO responsavel (nome, cpf_cnpj, rg, fisica_juridica, data_nascimento, profissao, endereco, observacao, email, telefones) values (:nome, :cpfCnpj, :rg, :fisicaJuridica, :dataNascimento, :profissao, :endereco, :observacao, :email, :telefones)";
        jdbcClient.sql(sql)
        .param("nome", responsavel.getNome())
        .param("cpfCnpj", responsavel.getCpfCnpj().getValor())
        .param("rg", responsavel.getRg().getValor())
        .param("fisicaJuridica", responsavel.getFisicaJuridica().getValor())
        .param("dataNascimento", responsavel.getDataNascimento())
        .param("profissao", responsavel.getProfissao())
        .param("endereco", responsavel.getEndereco())
        .param("observacao", responsavel.getObservacao())
        .param("email", responsavel.getEmail().getValor())
        .param("telefones", String.join(",",responsavel.getTelefones().stream().map(Telefone::getValor).toList())).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado).get();
    }

    public Responsavel update(Responsavel responsavel) {
        String sql = "UPDATE paciente SET , nome = :nome, cpf_cnpj = :cpfCnpj, rg = :rg, fisica_juridica = :fisicaJuridica, data_nascimento = :dataNascimento, profissao = :profissao, endereco = :endereco, observacao = :observacao, email = :email, telefone = :telefones WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id", responsavel.getId())
            .param("nome", responsavel.getNome())
            .param("cpfCnpj", responsavel.getCpfCnpj().getValor())
            .param("rg", responsavel.getRg().getValor())
            .param("fisicaJuridica", responsavel.getFisicaJuridica().getValor())
            .param("dataNascimento", responsavel.getDataNascimento())
            .param("profissao", responsavel.getProfissao())
            .param("endereco", responsavel.getEndereco())
            .param("observacao", responsavel.getObservacao())
            .param("email", responsavel.getObservacao())
            .param("telefones", responsavel.getObservacao()).update();
        return findById(responsavel.getId()).get();
    }

    public int delete(Long id) {
        jdbcClient.sql("DELETE FROM paciente_responsavel WHERE id_responsavel = ?").param(id).update();
        return jdbcClient.sql("DELETE FROM responsavel WHERE id = ?").param(id).update();
    }

    public int deleteAll() {
        jdbcClient.sql("DELETE FROM paciente_responsavel").update();
        return jdbcClient.sql("DELETE FROM responsavel").update();
    }


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
