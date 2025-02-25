package br.com.jjohnnys.sgap_core.paciente.domain;

import java.time.LocalDate;
import java.util.Set;

import br.com.jjohnnys.sgap_core.paciente.domain.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Responsavel {
    private Long id;
    private String nome;
    private CpfCnpj cpfCnpj;
    private Rg rg;
    private FisicaJuridicaEnum fisicaJuridica;
    private LocalDate dataNascimento;
    private String profissao;
    private String endereco;
    private String observacao;
    private Email email;
    private Set<Telefone> telefones;
    
}
