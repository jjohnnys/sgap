package br.com.jjohnnys.sgap_core.agenda.infrastructure;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jjohnnys.sgap_core.agenda.application.gateways.HorarioAtendimentoGateways;
import br.com.jjohnnys.sgap_core.agenda.domain.HorarioAtendimento;
import br.com.jjohnnys.sgap_core.agenda.infrastructure.jdbc.HorarioAtendimentoJDBC;

@Component
public class HorarioAtendimentoRepositoryGateways implements HorarioAtendimentoGateways {

    @Autowired    
    private HorarioAtendimentoJDBC agendaJDBC;

    @Override
    public void salvar(HorarioAtendimento agenda) {
        if (agenda.getId() == null)
            agendaJDBC.insert(agenda);
        else
            agendaJDBC.update(agenda);        
    }

    @Override
    public void excluir(HorarioAtendimento agenda) {
        agendaJDBC.delete(agenda.getId());
    }

    @Override
    public HorarioAtendimento buscarPorPaciente(Long id) {
        return agendaJDBC.findById(id);

    }

    @Override
    public boolean existeAgenda(LocalTime horaInicio, LocalTime horaFim, String diaDaSemana) {
        return agendaJDBC.existsTime(horaInicio, horaFim, diaDaSemana);   
    }
    
}
