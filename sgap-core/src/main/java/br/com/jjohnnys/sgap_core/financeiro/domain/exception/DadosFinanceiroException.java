package br.com.jjohnnys.sgap_core.financeiro.domain.exception;

public class DadosFinanceiroException extends RuntimeException {

    public DadosFinanceiroException(String mensagem) {
        super(String.format("Erro para dados de financeiro %s", mensagem));
       }
    
}
