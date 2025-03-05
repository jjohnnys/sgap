package br.com.jjohnnys.sgap_core.financeiro.domain.enums;

import java.util.Optional;

import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;

public enum PlanoEnum {
    SEMANAL,
    QUINZENAL,
    SOCIAL,
    VOLUNTARIO;

    public static PlanoEnum getGeneroEnumPorValor(String valor) {
        if(valor == null) throw new IllegalArgumentException("Plano não pode ser nulo");
        Optional<PlanoEnum> planoEnum = Optional.of(PlanoEnum.valueOf(valor.toUpperCase()));
        return planoEnum.orElseThrow(() ->  new DadosFinanceiroException(valor));
    }
}
