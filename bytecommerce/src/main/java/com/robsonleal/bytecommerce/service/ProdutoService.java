package com.robsonleal.bytecommerce.service;

import com.robsonleal.bytecommerce.dto.ProdutoDTO;
import com.robsonleal.bytecommerce.exception.ResourceNotFoundException;
import com.robsonleal.bytecommerce.model.Produto;
import com.robsonleal.bytecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaService categoriaService;
    private final ModelMapper modelMapper;

    public ProdutoDTO criar(ProdutoDTO dto) {
        if (verificarSeCategoriaExiste(dto.getCategoria().getId())) {
            Produto entity = modelMapper.map(dto, Produto.class);
            return modelMapper.map(repository.save(entity), ProdutoDTO.class);
        }

        throw new ResourceNotFoundException(
                String.format("Categoria id: %d não encontrada!", dto.getCategoria().getId()));
    }

    public List<ProdutoDTO> buscarTodos() {
        List<Produto> produtos = repository.findAll();
        List<ProdutoDTO> produtosDTO = new ArrayList<>();

        for (Produto produto : produtos) {
            produtosDTO.add(modelMapper.map(produto, ProdutoDTO.class));
        }

        return produtosDTO;
    }

    public ProdutoDTO update(Long id, ProdutoDTO request) {
        Produto produto = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Produto id: %d não encontrado!", id)));

        if (!verificarSeCategoriaExiste(request.getCategoria().getId())) {
            throw new ResourceNotFoundException(
                    String.format("Categoria id: %d não encontrada!", request.getCategoria().getId()));
        }

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setCategoria(categoriaService.buscarPorId(request.getCategoria().getId()));

        repository.save(produto);

        return modelMapper.map(produto, ProdutoDTO.class);
    }

    private boolean verificarSeCategoriaExiste(Long categoriaId) {
        return categoriaService.existeCategoria(categoriaId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Produto id: %d não encontrado!", id)));

        return modelMapper.map(produto, ProdutoDTO.class);
    }
}
