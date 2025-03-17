package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.ModoPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.ExcluirModoPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.CadastrarModoPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.ModoPagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.ModoPagamentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;

@SpringBootTest
public class AlterarModoPagamentoUseCaseTest {

    @Autowired
    private CadastrarPacienteUserCase cadastrarPacienteUserCase;
    @Autowired
    private ExcluirModoPagamentoUseCase alterarModoPagamentoUseCase;
    @Autowired
    private CadastrarModoPagamentoUseCase cadastrarModoPagamentoUseCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private FinanceiroDsGateways financeiroDsGateways;
    @Autowired
    private PacienteJDBC pacienteJDBC;
    @Autowired
    private ModoPagamentoJDBC modoPagamentoJDBC;

    @BeforeEach
    void setUp() {
        modoPagamentoJDBC.deleteAll();    
        pacienteJDBC.deleteAll();
    }

    @Test
    public void alterarModoPagamento() {
        ModoPagamento modoPagamento = cadastrarModoPagamentoSemanal();    
        Paciente paciente = pacienteDsGateway.findPacienteByNome("Dom Pedro II").get();
        ModoPagamentoDTO modoPagamentoDTO = new ModoPagamentoDTO(modoPagamento.getId(), modoPagamento.getIdPaciente(), PlanoEnum.QUINZENAL.name(), modoPagamento.getValorPorConsulta(), modoPagamento.getDiaDoMes());
        alterarModoPagamentoUseCase.execute(modoPagamentoDTO);
        ModoPagamento modoPagamentoSalvo = financeiroDsGateways.findModoPagamentoPorIdPaciente(paciente.getId());
        assertEquals(PlanoEnum.QUINZENAL, modoPagamentoSalvo.getPlano());
    }

    @Test    
    public void deveRetornarErroAoAlterarModopagamentoComCLienteDeIdDiferente() {
        ModoPagamento modoPagamento = cadastrarModoPagamentoSemanal();
        ModoPagamentoDTO modoPagamentoDTO = new ModoPagamentoDTO(5000L, modoPagamento.getIdPaciente(), PlanoEnum.QUINZENAL.name(), new BigDecimal(130), 10);
        assertThrows(DadosFinanceiroException.class, () -> alterarModoPagamentoUseCase.execute(modoPagamentoDTO));
    }

    
    private ModoPagamento cadastrarModoPagamentoSemanal() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        ModoPagamentoDTO modoPagamentoDTO = new ModoPagamentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO);
        ModoPagamento modoPagamentoSalvo = financeiroDsGateways.findModoPagamentoPorIdPaciente(paciente.getId());
        return modoPagamentoSalvo;
    }
    

    private Paciente criaPaciente(StatusAtendimentoEnum status) {
        PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", status.getValor(), "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
        cadastrarPacienteUserCase.execute(pacienteDTO);
        Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");
        return pacienteSalvo.get();
    }

}
