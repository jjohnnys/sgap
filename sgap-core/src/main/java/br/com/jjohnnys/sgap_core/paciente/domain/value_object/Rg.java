package br.com.jjohnnys.sgap_core.paciente.domain.value_object;

import java.util.List;

import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

public class Rg {

    @Getter
    private String valor;

    public Rg(String valor) {        
        if(StringUtils.isBlank(valor))
            throw new DadosPacienteException(String.format("O valor de RG não pode ser nulo")); 
        if(valor.length() < 9)    
            throw new DadosPacienteException(String.format("O valor %s para RG é invalido", valor));
        this.valor = valor;
    }
    
}
