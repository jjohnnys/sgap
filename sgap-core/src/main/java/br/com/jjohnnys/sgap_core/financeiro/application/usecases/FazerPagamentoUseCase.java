package br.com.jjohnnys.sgap_core.financeiro.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@Service
public class FazerPagamentoUseCase {

    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;

    @Transactional    
    public void execute(PagamentoDTO pagamentoDTO) {
        PlanoAtendimento planoAtendimento = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(pagamentoDTO.getIdPaciente());
        if(!planoAtendimento.getId().equals(pagamentoDTO.getIdPlanoAtendimento()))
            throw new DadosFinanceiroException("Relação plano atedimento e paciente inválido");
        if(!StatusAtendimentoEnum.ATIVO.equals(planoAtendimento.getPaciente().getStatus())) throw new DadosFinanceiroException("Não pode ser realizado pagamento de paciente não ATIVO");
        LocalDate dataPagamento = pagamentoDTO.getDataPagamento() != null ? pagamentoDTO.getDataPagamento() : LocalDate.now();
        Pagamento pagamento = new Pagamento(pagamentoDTO.getId(), planoAtendimento, dataPagamento, pagamentoDTO.getValor(), null);
        if(PlanoEnum.VOLUNTARIO.equals(planoAtendimento.getPlano())) throw new DadosFinanceiroException("Paciente com plano voluntário não pode ser feito o pagamento");
        BigDecimal valorAPagar = planoAtendimento.getValorAPagarNoMes();
        if(!(valorAPagar.compareTo(pagamento.getValor()) == 0))
        throw new DadosFinanceiroException(String.format("O valor a ser pago deve ser de %s", valorAPagar));
        pagamento.defineStatus(planoAtendimento.getDiaPagamento());
        financeiroDsGateways.savePagamento(pagamento);
    }
}
