package br.com.jjohnnys.sgap_core.paciente.application.enums;

import java.util.stream.Stream;

import lombok.Getter;

public enum FisicaJuridicaEnum {

    F("Fisica"), J("Juridica");

    @Getter
    private String descricao;

    FisicaJuridicaEnum(String descricao) {
        this.descricao = descricao;
    }

    public Character getValor() {
        return descricao.charAt(0);
    }

    public static FisicaJuridicaEnum getFisicaJuridicaEnumPorValor(Character valor) {
        return Stream.of(FisicaJuridicaEnum.values())
        .filter(fisicaJuridicaEnum -> fisicaJuridicaEnum.getValor().equals(valor))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(String.format("Informacao %s sobre pessoa Fisica ou Juridica invalido: ", valor)));
    }
    
}
