package br.com.jjohnnys.sgap_core.paciente.domain.value_object;

import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

public class Email {

    @Getter
    private String valor;

    public Email(String valor) {        
        if(StringUtils.isBlank(valor)) return;
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";    
        if(!valor.matches(regexPattern))
            throw new DadosPacienteException(String.format("Email %s invalido", valor));
        this.valor = valor;
    }
    
}
