package br.com.jjohnnys.sgap_core.agenda.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

import br.com.jjohnnys.sgap_core.agenda.domain.enums.DiasDaSemanaEnum;
import br.com.jjohnnys.sgap_core.paciente.domain.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HorarioAtendimento {

    private Long id;
    private Paciente paciente;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private DiasDaSemanaEnum diaDaSemana;
    
}
