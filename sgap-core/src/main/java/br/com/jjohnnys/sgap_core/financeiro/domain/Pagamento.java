package br.com.jjohnnys.sgap_core.financeiro.domain;

import java.math.BigDecimal;
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
    private BigDecimal valor;
    private StatusPagamentoEnum status;


    public void defineStatus(int diadoMes) {
        LocalDate dataPagamento = data != null ? data : LocalDate.now();
        this.status = dataPagamento.getDayOfMonth() > diadoMes ? StatusPagamentoEnum.ATRAZADO : StatusPagamentoEnum.PAGO;
    }
    
}
