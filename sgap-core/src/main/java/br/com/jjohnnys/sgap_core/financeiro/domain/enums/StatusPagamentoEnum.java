package br.com.jjohnnys.sgap_core.financeiro.domain.enums;

import java.util.Optional;

import br.com.jjohnnys.sgap_core.financeiro.domain.exception.DadosFinanceiroException;
import io.micrometer.common.util.StringUtils;

public enum StatusPagamentoEnum {    
    PAGO,
    ATRAZADO,
    PENDENTE;

    public static StatusPagamentoEnum getStatusPagamentoEnum(String status) {
        if(StringUtils.isBlank(status)) return null;
        return Optional.of(StatusPagamentoEnum.valueOf(status.toUpperCase())).
            orElseThrow(() -> new DadosFinanceiroException("Status paramento invalido: " + status));
    }
}
