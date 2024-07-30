package com.robsonleal.bytebank.service;

import com.robsonleal.bytebank.dto.OperacaoRespose;
import com.robsonleal.bytebank.dto.TipoOperacao;
import com.robsonleal.bytebank.execption.BussinessException;
import com.robsonleal.bytebank.execption.ResourceNotFoundException;
import com.robsonleal.bytebank.model.Cliente;
import com.robsonleal.bytebank.model.Conta;
import com.robsonleal.bytebank.repository.ClienteRepository;
import com.robsonleal.bytebank.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;

    public OperacaoRespose sacar(Long clienteId, BigDecimal valor) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Cliente id: %d não encontrado!", clienteId)));

        Conta conta = cliente.getConta();

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new BussinessException("Saldo indisponível!");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        OperacaoRespose respose = new OperacaoRespose();
        respose.setOperacao(TipoOperacao.SAQUE);
        respose.setMensagem("Saque realizado com sucesso! Valor restante em conta R$ " + conta.getSaldo().toString());

        return respose;
    }

    public OperacaoRespose depositar(Long clienteId, BigDecimal valor) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Cliente id: %d não encontrado!", clienteId)));

        Conta conta = cliente.getConta();
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        OperacaoRespose respose = new OperacaoRespose();
        respose.setOperacao(TipoOperacao.DEPOSITO);
        respose.setMensagem("Depósito realizado com sucesso!");

        return respose;
    }

}
