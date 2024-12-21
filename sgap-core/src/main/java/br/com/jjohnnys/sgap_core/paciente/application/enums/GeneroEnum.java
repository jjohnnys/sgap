package br.com.jjohnnys.sgap_core.paciente.application.enums;

import java.util.stream.Stream;

import lombok.Getter;

public enum GeneroEnum {

    MASCULINO("Masculino"),
    FEMININO("Feminino");

    @Getter
    private String valor;

    GeneroEnum(String valor) {
        this.valor = valor;
    }

    public static GeneroEnum getGeneroEnumPorValor(String valor) {
        return Stream.of(GeneroEnum.values())
        .filter(generoEnum -> generoEnum.getValor().equals(valor))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(String.format("O valor %s para genero e invalido: ", valor)));
    }
    
}
