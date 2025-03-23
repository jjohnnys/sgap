package br.com.jjohnnys.sgap_core.financeiro.domain;

import java.math.BigDecimal;

import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanoAtendimento {
    private Long id;
    private Paciente paciente;
    private PlanoEnum plano;
    private BigDecimal valorPorConsulta;
    private Integer diaPagamento;

    public void validaCamposNulos() {        
        if(this.paciente.getId() == null || this.plano == null || this.diaPagamento == null)
            throw new DadosFinanceiroException("Para modo de pagamento todos os campos Pacientem plano, data: Devem ser preenchidos");
    }

    public void validaValor() {
        if(this.valorPorConsulta.compareTo(new BigDecimal(0)) == -1)
            throw new DadosFinanceiroException("O valor não pode ser negativo");
        if(PlanoEnum.VOLUNTARIO.equals(this.plano) && !(this.valorPorConsulta.compareTo(new BigDecimal(0)) == 0))
                throw new DadosFinanceiroException("Pacientes com planos do tipo Voluntários não pode ter valor");
        if(this.valorPorConsulta.compareTo(new BigDecimal(0)) == 0 && !PlanoEnum.VOLUNTARIO.equals(this.plano))
            throw new DadosFinanceiroException("Informe o valor para o plano do paciente");
    }

    public BigDecimal getValorAPagarNoMes() {
        return this.valorPorConsulta.multiply(new BigDecimal(plano.getQuantidadePorSemana()));
    }
     
}
