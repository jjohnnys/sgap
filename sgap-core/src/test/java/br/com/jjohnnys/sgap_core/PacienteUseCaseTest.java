package br.com.jjohnnys.sgap_core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.repository.jdbc.PacienteRepository;
import br.com.jjohnnys.sgap_core.paciente.use_case.CadastrarPacienteUserCase;

@SpringBootTest
public class PacienteUseCaseTest {

        @Autowired
        private CadastrarPacienteUserCase  cadastrarPacienteUserCase;
        @Autowired
        private PacienteRepository pacienteRepository;

        @Test
        public void criarUsuarioTest() {
                Paciente paciente = new Paciente(null, "Dom Pedro II", "111.111.111.11", "11.111.111-1", LocalDate.of(1925, 12, 02), "Super Doutor", "Imperador", "Masculino", "Rua do Imperador", "CDF");
                cadastrarPacienteUserCase.execute(paciente);
                Paciente pacienteSalvo = pacienteRepository.findByNome("Dom Pedro II");
                assertEquals("Dom Pedro II", pacienteSalvo.getNome());

        }


    
}
