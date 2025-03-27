package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PlanoAtendimentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.gateways.FinanceiroDsGateways;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.CadastroPlanoAtendimentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.ExcluirPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.FazerPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PlanoAtendimentoJDBC;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PagamentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;

@SpringBootTest
public class ExcluirPagamentoUseCaseTest {

    @Autowired
    private CadastrarPacienteUserCase cadastrarPacienteUserCase;
    @Autowired
    private CadastroPlanoAtendimentoUseCase cadastroPlanoAtendimentoUseCase;
    @Autowired
    private ExcluirPagamentoUseCase excluirPagamentoUseCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private PacienteJDBC pacienteJDBC;
    @Autowired
    private PlanoAtendimentoJDBC planoAtendimentoJDBC;
    @Autowired
    private FazerPagamentoUseCase fazerPagamentoUseCase;
    @Autowired
    private PagamentoJDBC pagamentoJDBC;

    @BeforeEach
    void setUp() {
        pagamentoJDBC.deleteAll();
        planoAtendimentoJDBC.deleteAll();    
        pacienteJDBC.deleteAll();
    }

    @Test
    public void fazerPagamentoTest() {
        Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);
        PlanoAtendimentoDTO planoAtendimentoDTO = new PlanoAtendimentoDTO(null, paciente.getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 10);
        cadastroPlanoAtendimentoUseCase.execute(planoAtendimentoDTO);
        PlanoAtendimento planoAtendimento = planoAtendimentoJDBC.findByIdPaciente(paciente.getId());        
        PagamentoDTO pagamentoDTO = new PagamentoDTO(null, paciente.getId(), planoAtendimento.getId(), LocalDate.of(2025,03, 10), new BigDecimal(520));
        fazerPagamentoUseCase.execute(pagamentoDTO);
        Pagamento pagamento = pagamentoJDBC.findByIdPaciente(paciente.getId()).get(0);
        excluirPagamentoUseCase.execute(pagamento.getId());
        List<Pagamento> pagamentoSalvo = pagamentoJDBC.findByIdPaciente(paciente.getId());
        assertTrue(pagamentoSalvo.isEmpty());

    }

    private Paciente criaPaciente(StatusAtendimentoEnum status) {
        PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", status.getValor(), "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
        cadastrarPacienteUserCase.execute(pacienteDTO);
        Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");
        return pacienteSalvo.get();
    }
    
}
