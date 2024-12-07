package br.com.jjohnnys.sgap_core.paciente.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

@Repository
public class PacienteJDBC implements PacienteRepository {

    @Autowired
    private JdbcClient jdbcClient;

    public Paciente findById(Long id) {
        return jdbcClient.sql("SELECT * FROM paciente WHERE id = ?").param(id).query(new PacienteMapper()).optional().get();
    }

    public Paciente findByNome(String nome) {
        return jdbcClient.sql("SELECT * FROM paciente WHERE nome = ?").param(nome).query(new PacienteMapper()).optional().get();
    }

    public Paciente insert(Paciente paciente) {
        String sql = "INSERT INTO paciente (nome, cpf, rg, data_nascimento, escolaridade, profissao, genero, endereco, observacao) values (:nome, :cpf, :rg, :dataNascimento, :escolaridade, :profissao, :genero, :endereco, :observacao)";
        jdbcClient.sql(sql)
        .param("nome", paciente.getNome())
        .param("cpf", paciente.getCpf())
        .param("rg", paciente.getRg())
        .param("dataNascimento", paciente.getDataNascimento())
        .param("escolaridade", paciente.getEscolaridade())
        .param("profissao", paciente.getProfissao())
        .param("genero", paciente.getGenero())
        .param("endereco", paciente.getEndereco())
        .param("observacao", paciente.getObservacao()).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado);
    }

    public Paciente update(Paciente paciente) {
        String sql = "UPDATE paciente SET , nome = :nome, cpf = :cpf, rg = :rg, dataNascimento = :dataNascimento, escolaridade = :escolaridade, profissao = :profissao, genero = :genero, endereco = :endereco, observacao = :observacao WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id", paciente.getId())
            .param("nome", paciente.getNome())
            .param("cpf", paciente.getCpf())
            .param("rg", paciente.getRg())
            .param("dataNascimento", paciente.getCpf())
            .param("escolaridade", paciente.getEscolaridade())
            .param("profissao", paciente.getProfissao())
            .param("genero", paciente.getGenero())
            .param("endereco", paciente.getEndereco())
            .param("observacao", paciente.getObservacao()).update();
        return findById(paciente.getId());
    }
    

    
}
