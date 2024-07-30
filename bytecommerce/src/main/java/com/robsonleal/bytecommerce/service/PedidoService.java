package com.robsonleal.bytecommerce.service;

import com.robsonleal.bytecommerce.dto.ItemPedidoDTO;
import com.robsonleal.bytecommerce.dto.PedidoDTO;
import com.robsonleal.bytecommerce.exception.BussinessException;
import com.robsonleal.bytecommerce.exception.ResourceNotFoundException;
import com.robsonleal.bytecommerce.integration.PedidoProducer;
import com.robsonleal.bytecommerce.model.Cliente;
import com.robsonleal.bytecommerce.model.Estoque;
import com.robsonleal.bytecommerce.model.ItemPedido;
import com.robsonleal.bytecommerce.model.Pedido;
import com.robsonleal.bytecommerce.model.StatusPedido;
import com.robsonleal.bytecommerce.repository.ClienteRepository;
import com.robsonleal.bytecommerce.repository.EstoqueRepository;
import com.robsonleal.bytecommerce.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstoqueRepository estoqueRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final PedidoProducer pedidoProducer;

    public PedidoDTO criarPedido(Long clienteId, List<ItemPedidoDTO> itensPedidosRequest) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() ->
            new ResourceNotFoundException(String.format("Cliente id: %d não encontrado!", clienteId)));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
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
        pedido.setStatus(StatusPedido.ENVIADO_PARA_VALIDACAO);
        pedido = pedidoRepository.save(pedido);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setDataPedido(pedido.getDataPedido());
        pedidoDTO.setStatus(pedido.getStatus());
        pedidoDTO.setClienteId(pedido.getCliente().getId());
        pedidoDTO.setClienteCpf(pedido.getCliente().getCpf());
        pedidoDTO.setValorTotalPedido(pedido.getValorTotalPedido());
        pedidoDTO.setItensPedido(pedido.getItensPedido().stream().map(item ->
                modelMapper.map(item, ItemPedidoDTO.class)).toList());

        pedidoProducer.sendMessage(pedidoDTO);

        return pedidoDTO;
    }

    public void concluirPedido(PedidoDTO pedidoDTO) {

        Pedido pedido = pedidoRepository.findById(pedidoDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Pedido id: %d não encontrado!", pedidoDTO.getId())));

        pedido.setStatus(pedidoDTO.getStatus());

        if (StatusPedido.SUCESSO_VALIDACAO.equals(pedidoDTO.getStatus())) {

            log.info("Pedido id: {} concluído com sucesso", pedidoDTO.getId());

        } else if (StatusPedido.ERRO_VALIDACAO.equals(pedidoDTO.getStatus())) {

            for (ItemPedidoDTO item : pedidoDTO.getItensPedido()) {

                Estoque estoque = estoqueRepository.findByProdutoId(item.getProdutoId()).orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Produto id: %d não encontrado no estoque!", item.getProdutoId())));

                estoque.setQuantidade(estoque.getQuantidade() + item.getQuantidade());
                estoqueRepository.save(estoque);
            }

            log.info("Pedido id: {} não foi concluído", pedidoDTO.getId());
        }

        pedidoRepository.save(pedido);
    }
}
