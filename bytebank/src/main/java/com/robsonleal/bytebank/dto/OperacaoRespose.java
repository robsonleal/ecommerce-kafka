package com.robsonleal.bytebank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperacaoRespose {
    private TipoOperacao operacao;
    private String mensagem;
}
