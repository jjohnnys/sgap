package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PlanoAtendimentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.CadastroPlanoAtendimentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PlanoAtendimentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;

@SpringBootTest
public class CadastrarModoPagamentoUseCaseTest {

    @Autowired
    private CadastrarPacienteUserCase cadastrarPacienteUserCase;
    @Autowired
    private CadastroPlanoAtendimentoUseCase cadastrarModoPagamentoUseCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;
    @Autowired
    private PacienteJDBC pacienteJDBC;
    @Autowired
    private PlanoAtendimentoJDBC modoPagamentoJDBC;

    @BeforeEach
    void setUp() {
        modoPagamentoJDBC.deleteAll();    
        pacienteJDBC.deleteAll();
    }


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
    

    private Paciente criaPaciente(StatusAtendimentoEnum status) {
        PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", status.getValor(), "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
        cadastrarPacienteUserCase.execute(pacienteDTO);
        Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");
        return pacienteSalvo.get();
    }

}
