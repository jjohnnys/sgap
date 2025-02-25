package br.com.jjohnnys.sgap_core.paciente.domain.value_object;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.FisicaJuridicaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

public class CpfCnpj {

    @Getter
    private String valor;

    public CpfCnpj(String valor, FisicaJuridicaEnum fisicaJuridica) {
        try {            
            if(StringUtils.isBlank(valor))
                throw new DadosPacienteException(String.format("O valor de %s não pode ser nulo", fisicaJuridica.getDescricao()));
            if(FisicaJuridicaEnum.F.equals(fisicaJuridica.F)) {
                CPFValidator cpfValidator = new CPFValidator(true);        
                cpfValidator.assertValid(valor);        
            } else {
                CNPJValidator cnpjValidator = new CNPJValidator(true);        
                cnpjValidator.assertValid(valor);
            }
        } catch (InvalidStateException e) {
            throw new DadosPacienteException(String.format("O valor %s e invalido para %s", valor, fisicaJuridica.getDescricao()));
        }            
        this.valor = valor;
    }
    
}
