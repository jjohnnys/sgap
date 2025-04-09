package br.com.jjohnnys.sgap_core.paciente.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import br.com.jjohnnys.sgap_core.paciente.domain.enums.EscolaridadeEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.GeneroEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.CpfCnpj;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Email;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Rg;
import br.com.jjohnnys.sgap_core.paciente.domain.value_object.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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
    private Email email;
    private Set<Telefone> telefones;
    
    public Boolean isDependente() {
        return dependente;
    }

    public Set<Responsavel> getResponsaveis() {
        return Collections.unmodifiableSet(responsaveis);
    }

    public Email getEmail() {
        return email;
    }
    public Set<Telefone> getTelefones() {
        return telefones;
    }
    public boolean validaDependenteResponsaveis() {
        if(!dependente && Optional.ofNullable(responsaveis).isPresent()) return false;
        if(dependente && Optional.ofNullable(responsaveis).isEmpty()) return false;
        return true;
    }

    public void adicionaResponsavel(Responsavel responsavel) { 
        responsaveis.add(responsavel);        
    }

    public boolean validaMudancaStatus(StatusAtendimentoEnum novoStatus) {
        if(novoStatus == null) return false;
        if(StatusAtendimentoEnum.ATIVO.equals(novoStatus)) return validaNovoStatusAtivo();        
        if(StatusAtendimentoEnum.CONCLUIDO.equals(novoStatus)) return validaNovoStatusConcluido();
        if(StatusAtendimentoEnum.INTERROMPIDO.equals(novoStatus)) return validaNovoStatusInterrompido();
        return true;
    }

    private boolean validaNovoStatusAtivo() {
        if(StatusAtendimentoEnum.ATIVO.equals(this.status)) return false;
        return true;
    }    

    private boolean validaNovoStatusConcluido() {
        if(StatusAtendimentoEnum.CONCLUIDO.equals(this.status) || StatusAtendimentoEnum.INTERROMPIDO.equals(this.status) ) return false;
        return true;
    }    

    private boolean validaNovoStatusInterrompido() {
        if(StatusAtendimentoEnum.CONCLUIDO.equals(this.status) || StatusAtendimentoEnum.INTERROMPIDO.equals(this.status) ) return false;
        return true;
    }    


}
