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

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.ModoPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.CadastrarModoPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.FazerPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.ModoPagamentoJDBC;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PagamentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;

@SpringBootTest
public class FazerPagamentoUseCaseTest {

    @Autowired
    private CadastrarPacienteUserCase cadastrarPacienteUserCase;
    @Autowired
    private CadastrarModoPagamentoUseCase cadastrarModoPagamentoUseCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private PacienteJDBC pacienteJDBC;
    @Autowired
    private ModoPagamentoJDBC modoPagamentoJDBC;
    @Autowired
    private FazerPagamentoUseCase fazerPagamentoUseCase;
    @Autowired
    private PagamentoJDBC pagamentoJDBC;

    @BeforeEach
    void setUp() {
        modoPagamentoJDBC.deleteAll();    
        pacienteJDBC.deleteAll();
    }

    @Test
    public void fazerPagamentoTest() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        ModoPagamentoDTO modoPagamentoDTO = new ModoPagamentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO);        
        PagamentoDTO pagamentoDTO = new PagamentoDTO(null, paciente.getId(), LocalDate.of(2025,03, 10), new BigDecimal(520), null);
        fazerPagamentoUseCase.execute(pagamentoDTO);

        Pagamento pagamentoSalvo = pagamentoJDBC.findByIdPaciente(paciente.getId());
        assertEquals(StatusPagamentoEnum.PAGO, pagamentoSalvo.getStatus());

    }

    @Test
    public void fazerPagamentoAtrasadoTest() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        ModoPagamentoDTO modoPagamentoDTO = new ModoPagamentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO);        
        PagamentoDTO pagamentoDTO = new PagamentoDTO(null, paciente.getId(), LocalDate.of(2025,03, 16), new BigDecimal(520), null);
        fazerPagamentoUseCase.execute(pagamentoDTO);

        Pagamento pagamentoSalvo = pagamentoJDBC.findByIdPaciente(paciente.getId());
        assertEquals(StatusPagamentoEnum.ATRAZADO, pagamentoSalvo.getStatus());

    }

    @Test
    public void deveRetornarErroAoFazerPagamentoDePacienteVoluntario() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        ModoPagamentoDTO modoPagamentoDTO = new ModoPagamentoDTO(null, paciente.getId(), PlanoEnum.VOLUNTARIO.name(), new BigDecimal(0), 10);
        cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO);        
        PagamentoDTO pagamentoDTO = new PagamentoDTO(null, paciente.getId(), LocalDate.now(), new BigDecimal(520), null);
        assertThrows(DadosFinanceiroException.class, () -> fazerPagamentoUseCase.execute(pagamentoDTO));
        

    }

    @Test
    public void deveRetornarErroAoFazerPagamentoComValorMenor() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        ModoPagamentoDTO modoPagamentoDTO = new ModoPagamentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130
        ), 10);
        cadastrarModoPagamentoUseCase.execute(modoPagamentoDTO);        
        PagamentoDTO pagamentoDTO = new PagamentoDTO(null, paciente.getId(), LocalDate.now(), new BigDecimal(510), null);
        assertThrows(DadosFinanceiroException.class, () -> fazerPagamentoUseCase.execute(pagamentoDTO));
        

    }

    private Paciente criaPaciente(StatusAtendimentoEnum status) {
        PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", status.getValor(), "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
        cadastrarPacienteUserCase.execute(pacienteDTO);
        Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");
        return pacienteSalvo.get();
    }
    
}
