package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class AlterarPlanoAtendimentoUseCaseTest {

    @Autowired
    private CadastrarPacienteUserCase cadastrarPacienteUserCase;
    @Autowired
    private CadastroPlanoAtendimentoUseCase alterarplanoAtendimentoUseCase;
    @Autowired
    private CadastroPlanoAtendimentoUseCase cadastrarplanoAtendimentoUseCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;
    @Autowired
    private PacienteJDBC pacienteJDBC;
    @Autowired
    private PlanoAtendimentoJDBC planoAtendimentoJDBC;

    @BeforeEach
    void setUp() {
        planoAtendimentoJDBC.deleteAll();    
        pacienteJDBC.deleteAll();
    }

    @Test
    public void alterarplanoAtendimento() {
        PlanoAtendimento planoAtendimento = cadastrarplanoAtendimentoSemanal();    
        Paciente paciente = pacienteDsGateway.findPacienteByNome("Dom Pedro II").get();
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(planoAtendimento.getId(), planoAtendimento.getPaciente().getId(), PlanoEnum.QUINZENAL.name(), planoAtendimento.getValorPorConsulta(), planoAtendimento.getDiaPagamento());
        alterarplanoAtendimentoUseCase.execute(planoAtendimentoDTO);
        PlanoAtendimento planoAtendimentoSalvo = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(paciente.getId());
        assertEquals(PlanoEnum.QUINZENAL, planoAtendimentoSalvo.getPlano());
    }

    @Test    
    public void deveRetornarErroAoAlterarplanoAtendimentoComCLienteDeIdDiferente() {
        PlanoAtendimento planoAtendimento = cadastrarplanoAtendimentoSemanal();
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(5000L, planoAtendimento.getPaciente().getId(), PlanoEnum.QUINZENAL.name(), new BigDecimal(130), 10);
        assertThrows(DadosFinanceiroException.class, () -> alterarplanoAtendimentoUseCase.execute(planoAtendimentoDTO));
    }

    
    private PlanoAtendimento cadastrarplanoAtendimentoSemanal() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        cadastrarplanoAtendimentoUseCase.execute(planoAtendimentoDTO);
        PlanoAtendimento planoAtendimentoSalvo = financeiroDsGateways.findPlanoAtendimentoPorIdPaciente(paciente.getId());
        return planoAtendimentoSalvo;
    }
    

    private Paciente criaPaciente(StatusAtendimentoEnum status) {
        PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", status.getValor(), "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
        cadastrarPacienteUserCase.execute(pacienteDTO);
        Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");
        return pacienteSalvo.get();
    }

}
