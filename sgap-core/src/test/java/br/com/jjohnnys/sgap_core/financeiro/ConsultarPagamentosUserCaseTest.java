package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.jjohnnys.sgap_core.SgapBaseTest;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PlanoAtendimentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.RelatorioPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.presenter.PagamentoPresenter;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.ConsultarPagamentosUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.FazerPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.InserirPlanoAtendimentoAoCLienteUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@SpringBootTest
@Transactional
@AutoConfigureJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConsultarPagamentosUserCaseTest extends SgapBaseTest {

    @Autowired    
    private ConsultarPagamentosUseCase consultarPagamentosUseCase;
    @Autowired
    private InserirPlanoAtendimentoAoCLienteUseCase inserirPlanoAtendimentoAoCLienteUseCase;
    @Autowired
    private FazerPagamentoUseCase fazerPagamentoUseCase;




    @Test
    public void consultarPagamentosTest() {
        fazerPagamentos();
        RelatorioPagamentoDTO relatorioPagamentoDTOs = new RelatorioPagamentoDTO("Dom Pedro II", null, null, null, null, null, null);
        PagamentoPresenter pagamentosImperdor = consultarPagamentosUseCase.execute(relatorioPagamentoDTOs);
        assertEquals("1040.00", String.valueOf(pagamentosImperdor.getTotal()));

    }

    public void fazerPagamentos() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        inserirPlanoAtendimentoAoCLienteUseCase.execute(planoAtendimentoDTO);
        PlanoAtendimento planoAtendimento = planoAtendimentoJDBC.findByIdPaciente(paciente.getId());        
        PagamentoDTO pagamentoDTO = new PagamentoDTO(null, paciente.getId(), planoAtendimento.getId(), LocalDate.of(2025,03, 10), new BigDecimal(520));
        fazerPagamentoUseCase.execute(pagamentoDTO);
        PagamentoDTO outroPagamentoDTO = new PagamentoDTO(null, paciente.getId(), planoAtendimento.getId(), LocalDate.of(2025,03, 16), new BigDecimal(520));
        fazerPagamentoUseCase.execute(outroPagamentoDTO);
    }
}
