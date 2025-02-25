package br.com.jjohnnys.sgap_core.financeiro.domain;

import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.value_object.Valor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModoPagamento {
    private Long id;
    private Long idPaciente;
    private PlanoEnum plano;
    private Valor valor;
    private LocalDate data;
}
