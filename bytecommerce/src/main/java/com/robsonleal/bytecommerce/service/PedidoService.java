package com.robsonleal.bytecommerce.service;

import com.robsonleal.bytecommerce.dto.ItemPedidoDTO;
import com.robsonleal.bytecommerce.dto.PedidoDTO;
import com.robsonleal.bytecommerce.exception.BussinessException;
import com.robsonleal.bytecommerce.exception.ResourceNotFoundException;
import com.robsonleal.bytecommerce.model.Estoque;
import com.robsonleal.bytecommerce.model.ItemPedido;
import com.robsonleal.bytecommerce.model.Pedido;
import com.robsonleal.bytecommerce.repository.EstoqueRepository;
import com.robsonleal.bytecommerce.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final EstoqueRepository estoqueRepository;
    private final ModelMapper modelMapper;

    public PedidoDTO criarPedido(List<ItemPedidoDTO> itensPedidosRequest) {
        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());

        for (ItemPedidoDTO item : itensPedidosRequest) {
            Estoque estoque = estoqueRepository.findByProdutoId(item.getProdutoId()).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Produto id: %d não encontrado no estoque!", item.getProdutoId())));

            if (estoque.getQuantidade() < item.getQuantidade()) {
                throw new BussinessException(String.format("Produto id: %d não possuí quantidade suficiente no estoque!", item.getProdutoId()));
            }

            estoque.setQuantidade(estoque.getQuantidade() - item.getQuantidade());
            estoqueRepository.save(estoque);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(estoque.getProduto());
            itemPedido.setQuantidade(item.getQuantidade());
            BigDecimal valorTotalPedido = BigDecimal.valueOf(estoque.getProduto().getPreco()).multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));
            itemPedido.setValorItens(valorTotalPedido);
            itemPedido.setPedido(pedido);
            pedido.getItensPedido().add(itemPedido);
        }

        BigDecimal valorTotal = new BigDecimal(BigInteger.ZERO);
        for (ItemPedido item : pedido.getItensPedido()) {
            valorTotal = valorTotal.add(item.getValorItens());
        }

        pedido.setValorTotalPedido(valorTotal);
        pedido = pedidoRepository.save(pedido);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setDataPedido(pedido.getDataPedido());
        pedidoDTO.setValorTotalPedido(pedido.getValorTotalPedido());
        pedidoDTO.setItensPedido(pedido.getItensPedido().stream().map(item ->
                modelMapper.map(item, ItemPedidoDTO.class)).toList());

        return pedidoDTO;
    }

}
