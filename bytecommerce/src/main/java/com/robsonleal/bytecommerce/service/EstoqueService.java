package com.robsonleal.bytecommerce.service;

import com.robsonleal.bytecommerce.dto.EstoqueDTO;
import com.robsonleal.bytecommerce.exception.BussinessException;
import com.robsonleal.bytecommerce.exception.ResourceNotFoundException;
import com.robsonleal.bytecommerce.model.Estoque;
import com.robsonleal.bytecommerce.model.Produto;
import com.robsonleal.bytecommerce.repository.EstoqueRepository;
import com.robsonleal.bytecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    public EstoqueDTO cadastrarProduto(Long produtoId, Integer quantidadeInicial) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Produto id: %d não encontrado!", produtoId)));

        Optional<Estoque> estoqueOptional = estoqueRepository.findByProdutoId(produtoId);

        if (estoqueOptional.isPresent()) {
            throw new BussinessException(String.format("Produto já cadastrado! id: %d", produtoId));
        } else {
            Estoque estoque = new Estoque();

            if (quantidadeInicial != null) {
                estoque.setProduto(produto);
                estoque.setQuantidade(quantidadeInicial);
            }

            return modelMapper.map(estoqueRepository.save(estoque), EstoqueDTO.class);
        }
    }
}
