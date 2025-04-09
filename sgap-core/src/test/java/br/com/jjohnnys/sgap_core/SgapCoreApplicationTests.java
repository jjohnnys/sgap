package br.com.jjohnnys.sgap_core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PagamentoJDBC;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PlanoAtendimentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.ResponsavelJDBC;

@SpringBootTest
class SgapCoreApplicationTests {

	@Autowired
    private PacienteJDBC pacienteJDBC;
    @Autowired
    private ResponsavelJDBC responsavelJDBC;
    @Autowired
    private PlanoAtendimentoJDBC planoAtendimentoJDBC;
    @Autowired
    private PagamentoJDBC pagamentoJDBC;

	

	@BeforeEach
    void setUp() {
		pagamentoJDBC.deleteAll();
		planoAtendimentoJDBC.deleteAll();
        responsavelJDBC.deleteAll();
        pacienteJDBC.deleteAll();
    }

}
