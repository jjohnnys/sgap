package br.com.jjohnnys.sgap_core.agenda.application.gateways;

import java.time.LocalTime;

import br.com.jjohnnys.sgap_core.agenda.domain.HorarioAtendimento;

public interface HorarioAtendimentoGateways {
    void salvar(HorarioAtendimento agenda);
    void excluir(HorarioAtendimento agenda);
    HorarioAtendimento buscarPorPaciente(Long id);
    boolean existeAgenda(LocalTime horaInicio, LocalTime horaFim, String diaDaSemana);

}
