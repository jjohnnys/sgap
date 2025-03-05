package br.com.jjohnnys.sgap_core.financeiro.domain;

import java.math.BigDecimal;

import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModoPagamento {
    private Long id;
    private Long idPaciente;
    private PlanoEnum plano;
    private BigDecimal valor;
    private Integer diaDoMes;

    public void validaCamposNulos() {        
        if(this.idPaciente == null || this.plano == null || this.diaDoMes == null)
            throw new DadosFinanceiroException("Para modo de pagamento todos os campos Pacientem plano, data: Devem ser preenchidos");
    }

    public void validaValor() {
        if(this.valor.compareTo(new BigDecimal(0)) == -1)
            throw new DadosFinanceiroException("O valor não pode ser negativo");
        if(PlanoEnum.VOLUNTARIO.equals(this.plano) && !(this.valor.compareTo(new BigDecimal(0)) == 0))
                throw new DadosFinanceiroException("Pacientes com planos do tipo Voluntários não pode ter valor");
        if(this.valor.compareTo(new BigDecimal(0)) == 0 && !PlanoEnum.VOLUNTARIO.equals(this.plano))
            throw new DadosFinanceiroException("Informe o valor para o plano do paciente");
    }
     
}
