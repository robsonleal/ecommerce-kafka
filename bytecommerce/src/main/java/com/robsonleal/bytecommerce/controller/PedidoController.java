package com.robsonleal.bytecommerce.controller;

import com.robsonleal.bytecommerce.dto.ItemPedidoDTO;
import com.robsonleal.bytecommerce.dto.PedidoDTO;
import com.robsonleal.bytecommerce.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/fazer-pedido")
    public ResponseEntity<PedidoDTO> fazerPedido(@RequestBody List<ItemPedidoDTO> itensPedidosRequest) {
        return ResponseEntity.ok(pedidoService.criarPedido(itensPedidosRequest));
    }

}
