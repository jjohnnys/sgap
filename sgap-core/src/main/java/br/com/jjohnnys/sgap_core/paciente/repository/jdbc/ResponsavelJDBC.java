package br.com.jjohnnys.sgap_core.paciente.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.paciente.domain.Responsavel;

@Repository
public class ResponsavelJDBC implements ResponsavelRepository {

    @Autowired
    private JdbcClient jdbcClient;

    public Responsavel findById(Long id) {
        return jdbcClient.sql("SELECT * FROM responsavel WHERE id = ?").param(id).query(new ResponsavelMapper()).optional().get();
    }

    public Responsavel findByNome(String nome) {
        return jdbcClient.sql("SELECT * FROM responsavel WHERE nome = ?").param(nome).query(new ResponsavelMapper()).optional().get();
    }

    public Responsavel insert(Responsavel responsavel) {
        String sql = "INSERT INTO responsavel (nome, cpf_cnpj, rg, fisica_juridica, data_nascimento, profissao, endereco, observacao) values (:nome, :cpfCnpj, :rg, :fisicaJuridica, :dataNascimento, :profissao, :endereco, :observacao)";
        jdbcClient.sql(sql)
        .param("nome", responsavel.getNome())
        .param("cpfCnpj", responsavel.getCpfCnpj().getValor())
        .param("rg", responsavel.getRg().getValor())
        .param("fisicaJuridica", responsavel.getFisicaJuridica().getValor())
        .param("dataNascimento", responsavel.getDataNascimento())
        .param("profissao", responsavel.getProfissao())
        .param("endereco", responsavel.getEndereco())
        .param("observacao", responsavel.getObservacao()).update();
        Long idCriado = jdbcClient.sql("SELECT lastval()").query(Long.class).single();
        return findById(idCriado);
    }

    public Responsavel update(Responsavel responsavel) {
        String sql = "UPDATE paciente SET , nome = :nome, cpf_cnpj = :cpfCnpj, rg = :rg, fisica_juridica = :fisicaJuridica, data_nascimento = :dataNascimento, profissao = :profissao, endereco = :endereco, observacao = :observacao WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id", responsavel.getId())
            .param("nome", responsavel.getNome())
            .param("cpfCnpj", responsavel.getCpfCnpj().getValor())
            .param("rg", responsavel.getRg().getValor())
            .param("fisicaJuridica", responsavel.getFisicaJuridica().getValor())
            .param("dataNascimento", responsavel.getDataNascimento())
            .param("profissao", responsavel.getProfissao())
            .param("endereco", responsavel.getEndereco())
            .param("observacao", responsavel.getObservacao()).update();
        return findById(responsavel.getId());
    }
    

    
}
