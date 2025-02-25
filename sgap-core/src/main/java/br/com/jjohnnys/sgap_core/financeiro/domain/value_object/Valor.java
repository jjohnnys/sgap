package br.com.jjohnnys.sgap_core.financeiro.domain.value_object;

import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;
import lombok.Getter;

public class Valor {

    @Getter
    private float valor;

    public Valor(Float valor) { 
        if(valor < 0)         
            throw new DadosPacienteException(String.format("Valor %s invalido", valor));
        this.valor = valor;
    }
    
}
