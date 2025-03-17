package br.com.jjohnnys.sgap_core.financeiro.application.usecases;


import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;

@Service
public class ConsultarPagamentos {

    public List<PagamentoDTO> execute(Long nomePaciente, Date dataInicio, Date dataFim, String status) {

        

        return null;
    }
    
}
