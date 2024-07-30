package com.robsonleal.bytebank.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsonleal.bytebank.dto.PedidoDTO;
import com.robsonleal.bytebank.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidosConsumer {

    private final PedidoService pedidoService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "analise-pedidos-entrada-v1", groupId = "my-group")
    public void listen(String message) throws JsonProcessingException {

        PedidoDTO dto = objectMapper.readValue(message, PedidoDTO.class);
        pedidoService.processarPedido(dto);

    }

}
