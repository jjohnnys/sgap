package br.com.jjohnnys.sgap_core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.paciente.application.dto.AlteraStatusDto;
import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.dto.ResponsavelDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.AlterarStatusPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.exception.DadosPacienteException;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.ResponsavelJDBC;

@SpringBootTest
public class CadastrarPacienteUseCaseTest {

        @Autowired
        private CadastrarPacienteUserCase  cadastrarPacienteUserCase;
        @Autowired
        private PacienteDsGateway pacienteDsGateway;
        @Autowired
        private PacienteJDBC pacienteJDBC;
        @Autowired
        private ResponsavelJDBC responsavelJDBC;
        @Autowired AlterarStatusPacienteUserCase alterarStatusPacienteUserCase;


        @BeforeEach
        void setUp() {
                responsavelJDBC.deleteAll();
                pacienteJDBC.deleteAll();
        }


        @Test
        public void criarUsuarioTest() {
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", "Ativo", "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
                cadastrarPacienteUserCase.execute(pacienteDTO);
                Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");
                assertEquals("Dom Pedro II", pacienteSalvo.get().getNome());
        }

        @Test
        public void criarUsuarioDependentTest() {
                ResponsavelDTO responsavelDTO = new ResponsavelDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Imperador", "Rua do Imperador", "Imperador", "imperador@brasil.com.br", Set.of("21111111111","11111111111"));        
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Princesa Isabel", "632.516.100-90", "11.111.111-2", 'F', LocalDate.of(1846, 07, 29), "Doutorado", "Feminino", "Princesa", "Rua da Princesa", "Ativo", "CDF", true, Set.of(responsavelDTO), "princesa@brasil.com.br", Set.of("2111111111","11111111112"));
                cadastrarPacienteUserCase.execute(pacienteDTO);
                Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Princesa Isabel");
                assertEquals("Princesa Isabel", pacienteSalvo.get().getNome());
                assertEquals("Dom Pedro II", pacienteSalvo.get().getResponsaveis().stream().findFirst().get().getNome());
        }

        @Test
        public void retorneErroAoCriarUsuarioDependentSemResponsavelTest() {
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Princesa Isabel", "632.516.100-90", "11.111.111-2", 'F', LocalDate.of(1846, 07, 29), "Doutorado", "Feminino", "Princesa", "Rua da Princesa", "Ativo", "CDF", true, null, "princesa@brasil.com.br", Set.of("21111111111","1111111111"));                
                assertThrows(DadosPacienteException.class, () -> cadastrarPacienteUserCase.execute(pacienteDTO));
        }

        @Test
        public void retorneErroAoCriarUsuarioNaoDependenteComOResponsavelTest() {
                ResponsavelDTO responsavelDTO = new ResponsavelDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Imperador", "Rua do Imperador", "Imperador", "imperador@brasil.com.br", Set.of("21111111111, 11111111111"));        
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Princesa Isabel", "632.516.100-90", "11.111.111-2", 'F', LocalDate.of(1846, 07, 29), "Doutorado", "Feminino", "Princesa", "Rua da Princesa", "Ativo", "CDF", false, Set.of(responsavelDTO), "princesa@brasil.com.br", Set.of("2111111111, 11111111111"));                
                assertThrows(DadosPacienteException.class, () -> cadastrarPacienteUserCase.execute(pacienteDTO));
        }

        @Test
        public void alteraStatusDeAtivoParaConcuido() {
                Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);        
                AlteraStatusDto alteraStatusDto = new AlteraStatusDto(paciente.getId(), StatusAtendimentoEnum.CONCLUIDO.getValor());
                alterarStatusPacienteUserCase.execute(alteraStatusDto);
                Paciente pacienteAlterado = pacienteDsGateway.findPacienteById(paciente.getId()).get();
                assertEquals(StatusAtendimentoEnum.CONCLUIDO, pacienteAlterado.getStatus());
        }

        @Test
        public void retorneErroAoAlterarPacienteInterrompidoParaConcluido() {
                Paciente paciente = criaPaciente(StatusAtendimentoEnum.INTERROMPIDO);
                AlteraStatusDto alteraStatusDto = new AlteraStatusDto(paciente.getId(), StatusAtendimentoEnum.CONCLUIDO.getValor());
                assertThrows(DadosPacienteException.class, () -> alterarStatusPacienteUserCase.execute(alteraStatusDto));
        }


        private Paciente criaPaciente(StatusAtendimentoEnum status) {
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", status.getValor(), "CDF", false, null, "imperador@brasil.com.br", Set.of("21111111111","11111111111"));
                cadastrarPacienteUserCase.execute(pacienteDTO);
                Optional<Paciente> pacienteSalvo = pacienteDsGateway.findPacienteByNome("Dom Pedro II");
                return pacienteSalvo.get();
        }
}
