package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@SpringBootTest
@Transactional
@AutoConfigureJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AlterarPlanoAtendimentoUseCaseTest extends SgapBaseTest {

    @Autowired
    private CadastrarPacienteUserCase cadastrarPacienteUserCase;
    @Autowired
    private InserirPlanoAtendimentoAoCLienteUseCase alterarplanoAtendimentoUseCase;
    @Autowired
    private InserirPlanoAtendimentoAoCLienteUseCase cadastrarplanoAtendimentoUseCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;

    

    @Test
    public void alterarplanoAtendimento() {
        PlanoAtendimento planoAtendimento = cadastrarPlanoAtendimentoSemanal();    
        Paciente paciente = pacienteDsGateway.findPacienteByNome("Dom Pedro II").get();
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(planoAtendimento.getId(), planoAtendimento.getPaciente().getId(), PlanoEnum.QUINZENAL.name(), planoAtendimento.getValorPorConsulta(), planoAtendimento.getDiaPagamento());
        alterarplanoAtendimentoUseCase.execute(planoAtendimentoDTO);
        PlanoAtendimento planoAtendimentoSalvo = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(paciente.getId());
        assertEquals(PlanoEnum.QUINZENAL, planoAtendimentoSalvo.getPlano());
    }

    @Test    
    public void deveRetornarErroAoAlterarplanoAtendimentoComCLienteDeIdDiferente() {
        PlanoAtendimento planoAtendimento = cadastrarPlanoAtendimentoSemanal();
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(5000L, planoAtendimento.getPaciente().getId(), PlanoEnum.QUINZENAL.name(), new BigDecimal(130), 10);
        assertThrows(DadosFinanceiroException.class, () -> alterarplanoAtendimentoUseCase.execute(planoAtendimentoDTO));
    }

    
    private PlanoAtendimento cadastrarPlanoAtendimentoSemanal() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        cadastrarplanoAtendimentoUseCase.execute(planoAtendimentoDTO);
        PlanoAtendimento planoAtendimentoSalvo = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(paciente.getId());
        return planoAtendimentoSalvo;
    }
}
