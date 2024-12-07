package br.com.jjohnnys.sgap_core.paciente.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Paciente {
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String escolaridade;
    private String genero;
    private String profissao;
    private String endereco;
    private String observacao;
}
