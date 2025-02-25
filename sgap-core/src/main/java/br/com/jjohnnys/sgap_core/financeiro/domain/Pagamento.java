package br.com.jjohnnys.sgap_core.financeiro.domain;

import java.time.LocalDate;

import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pagamento {

    private Long id;
    private Long idPaciente;
    private LocalDate data;
    private StatusPagamentoEnum status;
    
}
