package com.robsonleal.bytecommerce.service;

import com.robsonleal.bytecommerce.dto.ClienteDTO;
import com.robsonleal.bytecommerce.exception.ResourceNotFoundException;
import com.robsonleal.bytecommerce.model.Cliente;
import com.robsonleal.bytecommerce.repository.ClienteRepository;
import com.robsonleal.bytecommerce.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ModelMapper modelMapper;

    public ClienteDTO criar(ClienteDTO request) {
        Cliente cliente = modelMapper.map(request, Cliente.class);
        enderecoRepository.save(cliente.getEndereco());
        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    public ClienteDTO update(Long id, ClienteDTO request) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Cliente id: %d não encontrado!", id)));

        cliente.setNome(request.getNome());
        cliente.setSobrenome(request.getSobrenome());
        cliente.setDataNascimento(request.getDataNascimento());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());

        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    public List<ClienteDTO> buscarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(cliente -> modelMapper.map(cliente, ClienteDTO.class)).toList();
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Cliente id: %d não encontrado!", id)));
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}
