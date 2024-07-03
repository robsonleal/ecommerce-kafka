package com.robsonleal.bytecommerce.service;

import com.robsonleal.bytecommerce.dto.EnderecoDTO;
import com.robsonleal.bytecommerce.exception.ResourceNotFoundException;
import com.robsonleal.bytecommerce.model.Endereco;
import com.robsonleal.bytecommerce.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository repository;
    private final ModelMapper modelMapper;

    public EnderecoDTO criar(EnderecoDTO request) {
        Endereco endereco = modelMapper.map(request, Endereco.class);
        return modelMapper.map(repository.save(endereco), EnderecoDTO.class);
    }

    public List<EnderecoDTO> buscarTodos() {
        List<Endereco> enderecos = repository.findAll();
        return enderecos.stream().map(endereco -> modelMapper.map(endereco, EnderecoDTO.class)).toList();
    }

    public EnderecoDTO update(Long id, EnderecoDTO request) {
        Endereco endereco = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Endereço id: %d não encontrado!", id)));

        endereco.setRua(request.getRua());
        endereco.setNumero(request.getNumero());
        endereco.setCep(request.getCep());
        endereco.setBairro(request.getBairro());

        return modelMapper.map(repository.save(endereco), EnderecoDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public EnderecoDTO buscarPorId(Long id) {
        Endereco endereco = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Endereço id: %d não encontrado!", id)));
        return modelMapper.map(endereco, EnderecoDTO.class);
    }
}
