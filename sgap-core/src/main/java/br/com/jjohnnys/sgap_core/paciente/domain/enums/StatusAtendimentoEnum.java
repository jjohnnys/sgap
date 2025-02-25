package br.com.jjohnnys.sgap_core.paciente.domain.enums;

import java.util.stream.Stream;

import lombok.Getter;

public enum StatusAtendimentoEnum {

    ATIVO("Ativo"),
    CONCLUIDO("Concluido"),
    INTERROMPIDO("interrompido");

    @Getter
    private String valor;

    StatusAtendimentoEnum(String valor) {
        this.valor = valor;
    }

    public static StatusAtendimentoEnum getStatusAtendimentoEnumPorValor(String valor) {
        return Stream.of(StatusAtendimentoEnum.values())
        .filter(statusAtendimentoEnum -> statusAtendimentoEnum.getValor().equals(valor))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(String.format("O valor %s para status do paciente e invalido: ", valor)));
    }
    
}
