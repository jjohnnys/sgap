package br.com.jjohnnys.sgap_core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.dto.ResponsavelDTO;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.application.usecases.ExcluirPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;

@SpringBootTest
public class ExcluirPacienteUseCaseTest {

    @Autowired
    private ExcluirPacienteUserCase excluirPacienteUserCase;
    @Autowired
    private CadastrarPacienteUserCase  cadastrarPacienteUserCase;
    @Autowired
    private PacienteDsGateway pacienteDsGateway;

    @Test
    public void excluiPacienteEResponsavel() {

        ResponsavelDTO responsavelDTO = new ResponsavelDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1825, 12, 02), "Imperador", "Rua do Imperador", "Imperador", "imperador@brasil.com.br", Set.of("21111111111","11111111111"));        
        PacienteDTO pacienteDTO = new PacienteDTO(null, "Princesa Isabel", "632.516.100-90", "11.111.111-2", 'F', LocalDate.of(1846, 07, 29), "Doutorado", "Feminino", "Princesa", "Rua da Princesa", "Ativo", "CDF", true, Set.of(responsavelDTO), "princesa@brasil.com.br", Set.of("2111111111","11111111112"));
        cadastrarPacienteUserCase.execute(pacienteDTO);
        Paciente pacienteSalvo = pacienteDsGateway.findPacienteByNome("Princesa Isabel").get();
        assertEquals("Princesa Isabel", pacienteSalvo.getNome());
        assertEquals("Dom Pedro II", pacienteSalvo.getResponsaveis().stream().findFirst().get().getNome());

        excluirPacienteUserCase.execute(pacienteSalvo.getId());
        var paciente = pacienteDsGateway.findPacienteByNome("Princesa Isabel");
        assertNull(paciente);
    }
    
}
