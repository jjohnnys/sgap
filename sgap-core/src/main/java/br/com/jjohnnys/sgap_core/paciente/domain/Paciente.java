package br.com.jjohnnys.sgap_core.paciente.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import br.com.jjohnnys.sgap_core.paciente.application.enums.EscolaridadeEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.GeneroEnum;
import br.com.jjohnnys.sgap_core.paciente.application.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Paciente {
    private Long id;
    private String nome;
    private CpfCnpj cpfCnpj;
    private Rg rg;
    private FisicaJuridicaEnum fisicaJuridica;
    private LocalDate dataNascimento;
    private EscolaridadeEnum escolaridade;
    private GeneroEnum genero;
    private String profissao;
    private String endereco;
    private StatusAtendimentoEnum status;
    private String observacao;
    private Boolean dependente;
    private Set<Responsavel> responsaveis = new HashSet<>();

    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public CpfCnpj getCpfCnpj() {
        return cpfCnpj;
    }
    public Rg getRg() {
        return rg;
    }
    public FisicaJuridicaEnum getFisicaJuridica() {
        return fisicaJuridica;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public EscolaridadeEnum getEscolaridade() {
        return escolaridade;
    }
    public GeneroEnum getGenero() {
        return genero;
    }
    public String getProfissao() {
        return profissao;
    }
    public String getEndereco() {
        return endereco;
    }
    public StatusAtendimentoEnum getStatus() {
        return status;
    }
    public String getObservacao() {
        return observacao;
    }
    public Boolean isDependente() {
        return dependente;
    }
    public Set<Responsavel> getResponsaveis() {
        return Collections.unmodifiableSet(responsaveis);
    }  

    public boolean validaDependenteResponsaveis() {
        if(!dependente && Optional.ofNullable(responsaveis).isPresent()) return false;
        if(dependente && Optional.ofNullable(responsaveis).isEmpty()) return false;
        return true;
    }

    public void adicionaResponsavel(Responsavel responsavel) {       
        responsaveis.add(responsavel);        
    }
    
    

}
