package br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;

@Repository
public class PacienteJDBC {

    @Autowired
    private JdbcClient jdbcClient;

    public Paciente findById(Long id) {
        return jdbcClient.sql("SELECT * FROM paciente WHERE id = ?").param(id).query(new PacienteMapper()).optional().get();
    }

    public Paciente findByNome(String nome) {
        return jdbcClient.sql("SELECT * FROM paciente WHERE nome = ?").param(nome).query(new PacienteMapper()).optional().get();
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
        return findById(idCriado);
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
        return findById(paciente.getId());
    }

    public int deleteById(Long id) {
        return jdbcClient.sql("DELETE FROM paciente  WHERE id = ?").param(id).update();
    }

    public int deleteAll() {
        return jdbcClient.sql("DELETE FROM paciente").update();
    }
    

    
}
