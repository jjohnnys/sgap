package br.com.jjohnnys.sgap_core.agenda.application.dto;

import java.time.LocalTime;

import br.com.jjohnnys.sgap_core.agenda.domain.HorarioAtendimento;

public record HorarioAtendimentoDTO(Long id, String paciente, LocalTime horaInicio, LocalTime horaFim, String diaDaSemana ) {    

    public static HorarioAtendimentoDTO criarAgendaDTO(HorarioAtendimento agenda) {
        return new HorarioAtendimentoDTO(agenda.getId(), agenda.getPaciente().getNome(), agenda.getHoraInicio(), agenda.getHoraFim(), agenda.getDiaDaSemana().getDescricao());
    }

}
