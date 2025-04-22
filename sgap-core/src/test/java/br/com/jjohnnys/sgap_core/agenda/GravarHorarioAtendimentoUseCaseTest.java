package br.com.jjohnnys.sgap_core.agenda;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.jjohnnys.sgap_core.SgapBaseTest;
import br.com.jjohnnys.sgap_core.agenda.application.dto.HorarioAtendimentoDTO;
import br.com.jjohnnys.sgap_core.agenda.application.usecase.GravarHorarioAtendimentoUseCase;
import br.com.jjohnnys.sgap_core.agenda.domain.HorarioAtendimento;
import br.com.jjohnnys.sgap_core.agenda.infrastructure.jdbc.HorarioAtendimentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import br.com.jjohnnys.sgap_core.paciente.domain.enums.StatusAtendimentoEnum;

@SpringBootTest
@Transactional
@AutoConfigureJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GravarHorarioAtendimentoUseCaseTest extends SgapBaseTest {

    @Autowired
    private GravarHorarioAtendimentoUseCase agendarAtendimentosUseCase;
    @Autowired
    private HorarioAtendimentoJDBC horariosAtendimentosJDBC;

    
    @Test
    public void marcarHorariosDeAtendimentoComSucessoTest() {
       criaPaciente(StatusAtendimentoEnum.ATIVO);     
       HorarioAtendimentoDTO horarioAtendimentoDTO = new HorarioAtendimentoDTO(null, "Dom Pedro II", LocalTime.of(9, 30), LocalTime.of(10, 30), "Segunda-feira");
       agendarAtendimentosUseCase.execute(horarioAtendimentoDTO);
       HorarioAtendimento agenda = horariosAtendimentosJDBC.findByPacienteId(1L);
       assert agenda != null : "Erro ao agendar atendimento";
    }

    @Test
    public void deveRtorarErroAoTentarCadastrarHorarioJaMarcadoTest() {
       Paciente paciente = criaPaciente(StatusAtendimentoEnum.ATIVO);     
       HorarioAtendimentoDTO horarioAtendimentoDTO = new HorarioAtendimentoDTO(null, paciente.getNome(), LocalTime.of(9, 30), LocalTime.of(10, 30), "Segunda-feira");
       agendarAtendimentosUseCase.execute(horarioAtendimentoDTO);
       Paciente outroPaciente = criaOutroPaciente(StatusAtendimentoEnum.ATIVO); 
       HorarioAtendimentoDTO outroOorarioAtendimentoDTO = new HorarioAtendimentoDTO(null, outroPaciente.getNome(), LocalTime.of(9, 30), LocalTime.of(10, 30), "Segunda-feira"); 
       assertThrows(IllegalArgumentException.class, () -> agendarAtendimentosUseCase.execute(outroOorarioAtendimentoDTO), "Já existe um atendimento agendado para este horário.");       
    }


    
}
