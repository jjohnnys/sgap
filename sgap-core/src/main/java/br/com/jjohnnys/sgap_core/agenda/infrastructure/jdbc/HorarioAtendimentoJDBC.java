package br.com.jjohnnys.sgap_core.agenda.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import br.com.jjohnnys.sgap_core.agenda.domain.HorarioAtendimento;
import br.com.jjohnnys.sgap_core.agenda.domain.enums.DiasDaSemanaEnum;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;

@Repository
public class HorarioAtendimentoJDBC {

    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private PacienteJDBC pacienteJDBC;

    public void insert(HorarioAtendimento horariosAtendimento) {
        String sql = "INSERT INTO horariosAtendimentos (id_paciente, hora_inicio, hora_fim, dia_da_semana) VALUES (?, ?, ?, ?)";
        jdbcClient.sql(sql)
            .param(horariosAtendimento.getPaciente().getId())
            .param(horariosAtendimento.getHoraInicio())
            .param(horariosAtendimento.getHoraFim())
            .param(horariosAtendimento.getDiaDaSemana().getDescricao())
            .update();
    }

    public void update(HorarioAtendimento horariosAtendimento) {
        String sql = "UPDATE horariosAtendimentos SET hora_inicio = ?, hora_fim = ?, dia_da_semana = ? WHERE id = ?";
        jdbcClient.sql(sql)
            .param(horariosAtendimento.getHoraInicio())
            .param(horariosAtendimento.getHoraFim())
            .param(horariosAtendimento.getDiaDaSemana().getDescricao())
            .param(horariosAtendimento.getId())
            .update();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM horariosAtendimentos WHERE id = ?";
        jdbcClient.sql(sql)
            .param(id)
            .update();
    }
    public HorarioAtendimento findById(Long id) {
        String sql = "SELECT * FROM horariosAtendimentos WHERE id = ?";
        return jdbcClient.sql(sql)
            .param(id)
            .query(this::mapRow)
            .single();
    }

    public HorarioAtendimento findByPacienteId(Long pacienteId) {
        String sql = "SELECT * FROM horariosAtendimentos WHERE id_paciente = ?";
        return jdbcClient.sql(sql)
            .param(pacienteId)
            .query(this::mapRow)
            .single();
    }

    public List<HorarioAtendimento> findByDayOfWeek(String diaDaSemana) {
        String sql = "SELECT * FROM horariosAtendimentos WHERE dia_da_semana = ?";
        return jdbcClient.sql(sql)
            .param(diaDaSemana)
            .query(this::mapRow)
            .list();
    }

    public boolean existsTime(LocalTime horaInicio, LocalTime horaFim, String diaDaSemana) {
        String sql = "SELECT COUNT(*) FROM horariosAtendimentos WHERE hora_inicio >= ? AND hora_fim <= ? AND dia_da_semana = ?";
        return jdbcClient.sql(sql)
            .param(horaInicio)
            .param(horaFim)
            .param(diaDaSemana)
            .query((rs, rowNum) -> rs.getLong(1))
            .stream()
            .findFirst()
            .orElse(0L) > 0;
    }


    public HorarioAtendimento mapRow(ResultSet rs, int arg1) throws SQLException {
        return new HorarioAtendimento(
            rs.getLong("id"),
            pacienteJDBC.findById(rs.getLong("id_paciente")).get(),
            rs.getTime("hora_inicio").toLocalTime(),
            rs.getTime("hora_fim").toLocalTime(),
            DiasDaSemanaEnum.getPorDescricao(rs.getString("dia_da_semana"))
        );
    }
        
    
}
