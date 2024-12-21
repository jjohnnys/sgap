package br.com.jjohnnys.sgap_core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.paciente.application.dto.PacienteDTO;
import br.com.jjohnnys.sgap_core.paciente.application.enums.DepenRespEnum;
import br.com.jjohnnys.sgap_core.paciente.application.use_case.CadastrarPacienteUserCase;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.repository.jdbc.PacienteRepository;

@SpringBootTest
public class PacienteUseCaseTest {

        @Autowired
        private CadastrarPacienteUserCase  cadastrarPacienteUserCase;
        @Autowired
        private PacienteRepository pacienteRepository;

        @Test
        public void criarUsuarioTest() {
                PacienteDTO pacienteDTO = new PacienteDTO(null, "Dom Pedro II", "043.153.290-70", "11.111.111-1", 'F', LocalDate.of(1925, 12, 02), "Doutorado", "Masculino", "Imperador", "Rua do Imperador", "Ativo", "CDF", DepenRespEnum.MAIOR_IDADE.getValor());
                cadastrarPacienteUserCase.execute(pacienteDTO);
                Paciente pacienteSalvo = pacienteRepository.findByNome("Dom Pedro II");
                assertEquals("Dom Pedro II", pacienteSalvo.getNome());
        }


    
}
