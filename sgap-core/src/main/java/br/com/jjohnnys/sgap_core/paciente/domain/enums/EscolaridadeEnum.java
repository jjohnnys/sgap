package br.com.jjohnnys.sgap_core.paciente.domain.enums;

import java.util.stream.Stream;

import lombok.Getter;

public enum EscolaridadeEnum {
    
    ANALFABETO("Analfabeto") ,
    EDUCACAO_INFANTIL("Educação infantil"), 
    ENSINO_FUNDAMENTAL("Ensino fundamental"), 
    ENSINO_MEDIO("Ensino médio"),
    ENSINO_SUPERIOR("Ensino superior"),
    GRADUACAO("graduação"), 
    POS_GRADUACAO("Pós-graduação"), 
    MESTRADO("Mestrado"),
    DOUTORADO("Doutorado");

    @Getter
    private String valor;

    EscolaridadeEnum(String valor) {
        this.valor = valor;
    }

    public static EscolaridadeEnum getEscolaridadeEnumPorValor(String valor) {
        return Stream.of(EscolaridadeEnum.values())
        .filter(escolaridadeEnum -> escolaridadeEnum.getValor().equals(valor))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(String.format("O valor %s para escolaridade e invalido: ", valor)));
    }

}
