package br.com.jjohnnys.sgap_core.financeiro.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PagamentoDTO {
    private Long id;
    private Long idPaciente;
    private Long idPlanoAtendimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
}
