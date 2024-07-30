package com.robsonleal.bytebank.service;

import com.robsonleal.bytebank.dto.PedidoDTO;
import com.robsonleal.bytebank.dto.StatusPedido;
import com.robsonleal.bytebank.integration.PedidosProducer;
import com.robsonleal.bytebank.model.Cliente;
import com.robsonleal.bytebank.model.Conta;
import com.robsonleal.bytebank.repository.ClienteRepository;
import com.robsonleal.bytebank.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final PedidosProducer pedidosProducer;

    public void processarPedido(PedidoDTO dto) {

        Optional<Cliente> cliente = clienteRepository.findByCpf(dto.getClienteCpf());

        if (cliente.isPresent()) {
            if (cliente.get().getConta().getSaldo().compareTo(dto.getValorTotalPedido()) >= 0) {
                Conta conta = cliente.get().getConta();
                conta.setSaldo(conta.getSaldo().subtract(dto.getValorTotalPedido()));
                contaRepository.save(conta);
                dto.setStatus(StatusPedido.SUCESSO_VALIDACAO);
            } else {
                dto.setStatus(StatusPedido.ERRO_VALIDACAO);
                log.error("Saldo insuficiente!");
            }
        } else {
            dto.setStatus(StatusPedido.ERRO_VALIDACAO);
            log.error(String.format("Cliente documento: %s não encontrado!", dto.getClienteCpf()));
        }

        pedidosProducer.sendMessage(dto);
    }

}
