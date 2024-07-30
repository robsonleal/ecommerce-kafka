package com.robsonleal.bytebank.controller;

import com.robsonleal.bytebank.dto.OperacaoRespose;
import com.robsonleal.bytebank.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/clientes/{clienteId}")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @PostMapping("/sacar")
    public ResponseEntity<OperacaoRespose> sacar(@PathVariable Long clienteId, @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(contaService.sacar(clienteId, valor));
    }

    @PostMapping("/depositar")
    public ResponseEntity<OperacaoRespose> depositar(@PathVariable Long clienteId, @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(contaService.depositar(clienteId, valor));
    }

}
