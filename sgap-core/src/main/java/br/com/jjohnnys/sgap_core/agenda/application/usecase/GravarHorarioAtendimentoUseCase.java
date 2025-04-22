package br.com.jjohnnys.sgap_core.agenda.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jjohnnys.sgap_core.agenda.application.dto.HorarioAtendimentoDTO;
import br.com.jjohnnys.sgap_core.agenda.application.gateways.HorarioAtendimentoGateways;
import br.com.jjohnnys.sgap_core.agenda.domain.HorarioAtendimento;
import br.com.jjohnnys.sgap_core.agenda.domain.enums.DiasDaSemanaEnum;
import br.com.jjohnnys.sgap_core.paciente.application.gateways.PacienteDsGateway;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GravarHorarioAtendimentoUseCase {

    private final HorarioAtendimentoGateways horarioAtendimentoGateways;
    private final PacienteDsGateway pacienteDsGateway;

    public void execute(HorarioAtendimentoDTO agendaDTO) {
        Optional<Paciente> paciente = pacienteDsGateway.findPacienteByNome(agendaDTO.paciente());
        if(paciente.isEmpty())
            throw new IllegalArgumentException("Paciente não encontrado.");
        if(horarioAtendimentoGateways.existeAgenda(agendaDTO.horaInicio(), agendaDTO.horaFim(), agendaDTO.diaDaSemana()))
            throw new IllegalArgumentException("Já existe um atendimento agendado para este horário.");
        HorarioAtendimento agenda = new HorarioAtendimento(null, paciente.get(), agendaDTO.horaInicio(), agendaDTO.horaFim(), DiasDaSemanaEnum.getPorDescricao(agendaDTO.diaDaSemana()));
        horarioAtendimentoGateways.salvar(agenda);        
    }
    
}
