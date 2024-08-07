package com.robsonleal.bytecommerce.dto;

import com.robsonleal.bytecommerce.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Long id;
    private LocalDate dataPedido;
    private List<ItemPedidoDTO> itensPedido = new ArrayList<>();
    private BigDecimal valorTotalPedido;
    private Long clienteId;
    private String clienteCpf;
    private StatusPedido status;

}
