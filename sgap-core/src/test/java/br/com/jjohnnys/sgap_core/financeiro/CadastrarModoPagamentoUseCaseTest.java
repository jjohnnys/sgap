package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.jjohnnys.sgap_core.SgapBaseTest;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PlanoAtendimentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.InserirPlanoAtendimentoAoCLienteUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@SpringBootTest
@Transactional
@AutoConfigureJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CadastrarModoPagamentoUseCaseTest extends SgapBaseTest {
    @Autowired
    private InserirPlanoAtendimentoAoCLienteUseCase cadastrarModoPagamentoUseCase;
    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;


    @Test
    public void cadastrarModoPagamento() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO modoPagamentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO);
        PlanoAtendimento modoPagamentoSalvo = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(paciente.getId());
        assertTrue(modoPagamentoSalvo.getValorPorConsulta().compareTo(new BigDecimal(130)) == 0);
    }

    @Test    
    public void cadastrarModopagamentoVoluntario() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO modoPagamentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.VOLUNTARIO.name(), new BigDecimal(0), 10);        
        cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO);
        PlanoAtendimento modoPagamentoSalvo = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(paciente.getId());
        assertTrue(modoPagamentoSalvo.getValorPorConsulta().compareTo(new BigDecimal(0)) == 0);              
    }

    @Test    
    public void deveRetornarErroAoCadastrarModopagamentoVoluntarioComValor() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO modoPagamentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.VOLUNTARIO.name(), new BigDecimal(130), 10);        
        assertThrows(DadosFinanceiroException.class, () -> cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO));
    }

    @Test    
    public void deveRetornarErroAoCadastrarModopagamentoSemValor() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO modoPagamentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.QUINZENAL.name(), new BigDecimal(0), 10);        
        assertThrows(DadosFinanceiroException.class, () -> cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO));
    }
}
