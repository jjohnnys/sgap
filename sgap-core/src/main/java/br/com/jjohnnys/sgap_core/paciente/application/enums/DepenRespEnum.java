package br.com.jjohnnys.sgap_core.paciente.application.enums;

import java.util.stream.Stream;

import lombok.Getter;

public enum DepenRespEnum {
    DEPENDENTE("Dependente"),
    MAIOR_IDADE("Maior idade"),
    RESPOSAVEL("Resposavel");

    @Getter
    private String valor;

    DepenRespEnum(String valor) {
        this.valor = valor;
    }

    public static DepenRespEnum getDepenRespEnumPorValor(String valor) {
        return Stream.of(DepenRespEnum.values())
        .filter(depenRespEnum -> depenRespEnum.getValor().equals(valor))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Valor de responsabilidade invalido: " + valor));
    }
    
}
