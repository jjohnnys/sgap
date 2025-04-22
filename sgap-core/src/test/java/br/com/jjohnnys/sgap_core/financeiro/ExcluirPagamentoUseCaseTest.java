package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.jjohnnys.sgap_core.SgapBaseTest;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PlanoAtendimentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.InserirPlanoAtendimentoAoCLienteUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.ExcluirPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.FazerPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.PlanoAtendimento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@SpringBootTest
@Transactional
@AutoConfigureJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExcluirPagamentoUseCaseTest extends SgapBaseTest {

    @Autowired
    private InserirPlanoAtendimentoAoCLienteUseCase cadastroPlanoAtendimentoUseCase;
    @Autowired
    private ExcluirPagamentoUseCase excluirPagamentoUseCase;
    @Autowired
    private FazerPagamentoUseCase fazerPagamentoUseCase;    

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
}
