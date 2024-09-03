package com.robsonleal.bytebank.service;

import com.robsonleal.bytebank.dto.ClienteDTO;
import com.robsonleal.bytebank.dto.ClienteRequest;
import com.robsonleal.bytebank.model.Cliente;
import com.robsonleal.bytebank.model.Conta;
import com.robsonleal.bytebank.repository.ClienteRepository;
import com.robsonleal.bytebank.repository.ContaRepository;
import com.robsonleal.bytebank.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper;

    public ClienteRequest criar(ClienteRequest dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Conta conta = new Conta();
        conta.setSaldo(BigDecimal.ZERO);
        conta.setDataCriacao(LocalDate.now());
        conta.setNumero(gerarNumeroConta(cliente.getCpf()));
        cliente.setConta(conta);

        cliente.setEndereco(enderecoRepository.save(cliente.getEndereco()));
        contaRepository.save(conta);
        clienteRepository.save(cliente);

        return modelMapper.map(cliente, ClienteRequest.class);
    }

    private String gerarNumeroConta(String cpf) {
        String inicioCpf = cpf.substring(0, 3);

        LocalDate hoje = LocalDate.now();
        String hojeString = hoje.toString();
        hojeString = hojeString.replaceAll("-", "");

        int digito = (int) (Math.random() * 10);

        return inicioCpf + hojeString + "-" + digito;
    }

    public List<ClienteRequest> buscarTodos() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteRequest.class)).toList();
    }

    public ClienteRequest getById(Long id) {
        return modelMapper.map(clienteRepository.findById(id), ClienteRequest.class);
    }
}
