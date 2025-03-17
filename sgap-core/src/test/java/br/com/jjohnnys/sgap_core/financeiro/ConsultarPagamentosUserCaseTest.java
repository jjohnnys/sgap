package br.com.jjohnnys.sgap_core.financeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.financeiro.application.dtos.ModoPagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.dtos.PagamentoDTO;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.CadastrarModoPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.application.usecases.FazerPagamentoUseCase;
import br.com.jjohnnys.sgap_core.financeiro.domain.Pagamento;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.PlanoEnum;
import br.com.jjohnnys.sgap_core.financeiro.domain.enums.StatusPagamentoEnum;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PagamentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@SpringBootTest
public class ConsultarPagamentosUserCaseTest {

    @Autowired    
    private PagamentoJDBC pagamentoJDBC;
    @Autowired
    private CadastrarPacienteUserCase cadastrarPacienteUserCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;
    @Autowired
    private CadastrarModoPagamentoUseCase cadastrarModoPagamentoUseCase;
    @Autowired
    private FazerPagamentoUseCase fazerPagamentoUseCase;

    @Test
    public void consultarPagamentosTest() {
        Paciente imperador = criaPacientes(StatusAtendimentoEnum.ATIVO);
        
        LocalDate inicio = LocalDate.of(2025, 2, 10);
        LocalDate fim = LocalDate.of(2025, 3, 15);
        List<Pagamento> pagamentosImperdor = pagamentoJDBC.find(imperador.getId(), inicio, fim, StatusPagamentoEnum.PAGO);
        assertEquals(StatusPagamentoEnum.PAGO, pagamentosImperdor.get(0).getStatus());
        List<Pagamento> pagamentosImperdorAtrasados = pagamentoJDBC.find(imperador.getId(), inicio, fim, StatusPagamentoEnum.ATRAZADO);
        assertEquals(StatusPagamentoEnum.ATRAZADO, pagamentosImperdorAtrasados.get(0).getStatus());

    }


    private Paciente criaPacientes(StatusAtendimentoEnum status) {
                PacienteDTO impeadorDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", status.getValor(), "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
                cadastrarPacienteUserCase.execute(impeadorDTO);
                Optional<Paciente> imperadorSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");                
                ModoPagamentoDTO impModoPagamentoDTO = new ModoPagamentoDTO(null, imperadorSalvo.get().getId(), PlanoEnum.SEMANAL.name(), new BigDecimal(130), 11);
                cadastrarModoPagamentoUseCase.execute(impModoPagamentoDTO);
                PagamentoDTO pagamentoImperador = new PagamentoDTO(null,imperadorSalvo.get().getId(), LocalDate.of(2025,03, 15), new BigDecimal(520), null);
                fazerPagamentoUseCase.execute(pagamentoImperador);
                PagamentoDTO outroPagamentoImperador = new PagamentoDTO(null,imperadorSalvo.get().getId(), LocalDate.of(2025,02, 10), new BigDecimal(520), null);
                fazerPagamentoUseCase.execute(outroPagamentoImperador);                
                return imperadorSalvo.get();
        }

}
