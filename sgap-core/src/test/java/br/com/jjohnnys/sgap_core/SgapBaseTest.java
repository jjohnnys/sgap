package br.com.jjohnnys.sgap_core;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PagamentoJDBC;
import br.com.jjohnnys.sgap_core.financeiro.infrastructure.gateways.jdbc.PlanoAtendimentoJDBC;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.PacienteJDBC;
import br.com.jjohnnys.sgap_core.paciente.infrastructure.gateways.jdbc.ResponsavelJDBC;

public class SgapBaseTest {


    @Autowired
    protected PacienteJDBC pacienteJDBC;
    @Autowired
    protected ResponsavelJDBC responsavelJDBC;
    @Autowired
    protected PlanoAtendimentoJDBC planoAtendimentoJDBC;
    @Autowired
    protected PagamentoJDBC pagamentoJDBC;

    @BeforeEach
    void setUp() {
        pagamentoJDBC.deleteAll();
        planoAtendimentoJDBC.deleteAll();    
        responsavelJDBC.deleteAll();
        pacienteJDBC.deleteAll();
    }
    
}
