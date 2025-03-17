package br.com.jjohnnys.sgap_core.financeiro.domain.enums;

import java.util.Optional;

import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;

public enum PlanoEnum {
    SEMANAL {int quantidadePorSemana() {return 4;}},
    QUINZENAL{int quantidadePorSemana() {return 2;}},
    SOCIAL_SEMANAL {int quantidadePorSemana() {return 4;}},
    SOCIAL_QUINZENAL {int quantidadePorSemana() {return 2;}},
    VOLUNTARIO {int quantidadePorSemana() {return 0;}};

    abstract int quantidadePorSemana();

    public int getQuantidadePorSemana() {
        return this.quantidadePorSemana();
    }

    public static PlanoEnum getPlanoEnum(String valor) {
        if(valor == null) return null;
        Optional<PlanoEnum> planoEnum = Optional.of(PlanoEnum.valueOf(valor.toUpperCase()));
        return planoEnum.orElseThrow(() ->  new DadosFinanceiroException(String.format("O valor %s para genero é invalido", valor)));
    }
}
