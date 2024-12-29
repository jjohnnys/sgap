package br.com.jjohnnys.sgap_core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.dto.ResponsavelDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

@SpringBootTest
public class PacienteUseCaseTest {

        @Autowired
        private CadastrarPacienteUserCase  cadastrarPacienteUserCase;
        @Autowired
        private PacienteDsGateway pacienteDsGateway;

        @Test
        public void criarUsuarioTest() {
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", "Ativo", "CDF", false, null);
                cadastrarPacienteUserCase.execute(pacienteDTO);
                Paciente pacienteSalvo = pacienteDsGateway.findByNome("Dom Pedro II");
                assertEquals("Dom Pedro II", pacienteSalvo.getNome());
        }

        @Test
        public void criarUsuarioDependentTest() {
                ResponsavelDTO responsavelDTO = new ResponsavelDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Imperador", "Rua do Imperador", "Imperador");        
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Princesa Isabel", "632.516.100-90", "11.111.111-2", 'F', LocalDate.of(1846, 07, 29), "Doutorado", "Feminino", "Princesa", "Rua da Princesa", "Ativo", "CDF", true, Set.of(responsavelDTO));
                cadastrarPacienteUserCase.execute(pacienteDTO);
                Paciente pacienteSalvo = pacienteDsGateway.findByNome("Princesa Isabel");
                assertEquals("Princesa Isabel", pacienteSalvo.getNome());
                assertEquals("Dom Pedro II", pacienteSalvo.getResponsaveis().stream().findFirst().get().getNome());
        }


    
}
