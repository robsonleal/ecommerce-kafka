package com.robsonleal.bytebank.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsonleal.bytebank.dto.PedidoDTO;
import com.robsonleal.bytebank.execption.JsonConverterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidosProducer {

    private static final String TOPIC = "analise-pedidos-saida-v1";
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(PedidoDTO pedidoMessage) {
        log.info("Produzindo mensagem ...");

        try {
            String message = objectMapper.writeValueAsString(pedidoMessage);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException ex) {
            throw new JsonConverterException("Não foi possível converter a mensagem!", ex);
        }
    }
}
