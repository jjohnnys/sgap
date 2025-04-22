package br.com.jjohnnys.sgap_core.financeiro.application.usecases;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.RelatorioPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.presenter.PagamentoPresenter;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.RelatorioPagamentoJDBC;

@Service
public class ConsultarPagamentosUseCase {

    @Autowired
    private RelatorioPagamentoJDBC relatorioPagamentoJDBC;

    

    public PagamentoPresenter execute(RelatorioPagamentoDTO relatorioPagamentoDTO) {
        if(relatorioPagamentoDTO.getAnoInicio() == null && relatorioPagamentoDTO.getAnoFim() == null) {
            relatorioPagamentoDTO.setAnoInicio(LocalDate.now().getYear());
            relatorioPagamentoDTO.setAnoFim(LocalDate.now().getYear());
        }
        List<PagamentoPresenter.DadosPagamentoPaciente> dadosPagamentoPaciente = relatorioPagamentoJDBC.findRelatorio(relatorioPagamentoDTO); 
        

        var valorTotal = dadosPagamentoPaciente.stream()
                .map(PagamentoPresenter.DadosPagamentoPaciente::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        PagamentoPresenter pagamentoPresenters = new PagamentoPresenter();
        pagamentoPresenters.setTotal(valorTotal);
        pagamentoPresenters.setDadosPaciente(dadosPagamentoPaciente);
        return pagamentoPresenters;
    }
    
}
 