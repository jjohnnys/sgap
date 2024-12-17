package br.com.jjohnnys.sgap_core.paciente.domain.exception;

public class DadosPacienteException extends RuntimeException {

   public DadosPacienteException(String mensagem) {
    super(String.format("Erro para dados do paciente %s", mensagem));
   }

}
