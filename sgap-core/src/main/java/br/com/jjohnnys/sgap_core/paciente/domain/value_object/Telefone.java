package br.com.jjohnnys.sgap_core.paciente.domain.value_object;

import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

public class Telefone {

    @Getter
    private String valor;

    public Telefone(String valor) {        
        if(StringUtils.isBlank(valor)) return;
        if(valor.length() < 9)    
            throw new DadosPacienteException(String.format("Valor %s para telefone é invalido", valor));
        this.valor = valor;
    }
    
}
