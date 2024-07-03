package com.robsonleal.bytecommerce.controller;

import com.robsonleal.bytecommerce.dto.EstoqueDTO;
import com.robsonleal.bytecommerce.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping("/cadastrar-produto/{produtoId}")
    public ResponseEntity<EstoqueDTO> cadastrarProduto(@PathVariable Long produtoId, @RequestParam(required = false) Integer quantidadeInicial) {
        EstoqueDTO estoqueDTO = estoqueService.cadastrarProduto(produtoId, quantidadeInicial);
        return ResponseEntity.ok(estoqueDTO);
    }

}
