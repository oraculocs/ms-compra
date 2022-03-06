package com.mscompra.compra.service;

import com.mscompra.compra.model.Pedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

public class DadosMock {

    public Pedido getPedido(){
        return Pedido.builder()
                .nome("Carlos")
                .produto(1L)
                .valor(BigDecimal.TEN)
                .dataCompra(new Date())
                .cpfCliente("111.222.333-44")
                .cep("12345678")
                .email("sniper@yahoo.com")
                .build();
    }

    public Pedido getPedidoSalvo(){
        return Pedido.builder()
                .id(1L)
                .nome("Carlos")
                .produto(1L)
                .valor(BigDecimal.TEN)
                .dataCompra(new Date())
                .cpfCliente("111.222.333-44")
                .cep("12345678")
                .email("sniper@yahoo.com")
                .build();
    }
}
