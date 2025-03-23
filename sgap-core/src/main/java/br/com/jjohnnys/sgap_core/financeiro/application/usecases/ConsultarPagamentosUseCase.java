package br.com.jjohnnys.sgap_core.financeiro.application.usecases;


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

    

    public List<PagamentoPresenter> executeRelatorio(RelatorioPagamentoDTO relatorioPagamentoDTO) {
        if(relatorioPagamentoDTO.getAnoInicio() == null && relatorioPagamentoDTO.getAnoFim() == null) {
            relatorioPagamentoDTO.setAnoInicio(LocalDate.now().getYear());
            relatorioPagamentoDTO.setAnoFim(LocalDate.now().getYear());
        }
        List<PagamentoPresenter> pagamentoPresenters = relatorioPagamentoJDBC.findRelatorio(relatorioPagamentoDTO);        
        return pagamentoPresenters;
    }
    
}
 