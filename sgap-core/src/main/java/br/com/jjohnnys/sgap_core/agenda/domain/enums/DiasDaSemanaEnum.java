package br.com.jjohnnys.sgap_core.agenda.domain.enums;

import java.util.List;

public enum DiasDaSemanaEnum {
    SEGUNDA_FEIRA("Segunda-feira"),
    TERCA_FEIRA("Terça-feira"),
    QUARTA_FEIRA("Quarta-feira"),
    QUINTA_FEIRA("Quinta-feira"),
    SEXTA_FEIRA("Sexta-feira"),
    SABADO("Sábado"),
    DOMINGO("Domingo");

    private final String descricao;

    DiasDaSemanaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static DiasDaSemanaEnum getPorDescricao(String descricao) {        
        return List.of(DiasDaSemanaEnum.values()).stream()
                .filter(dia -> dia.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dia da semana inválido: " + descricao));
    }
}
