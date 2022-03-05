package com.mscompra.compra.service;

import com.mscompra.compra.model.Pedido;
import com.mscompra.compra.repository.PedidoRepository;
import com.mscompra.compra.service.rabbitmq.Producer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private Producer producer;

    private DadosMock mock = new DadosMock();

    @DisplayName("Salvar pedido com sucesso")
    @Test
    void deveSalverUmPedidoComSucesso(){
        var pedidoMok = mock.getPedido();

        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedidoMok);

        Pedido pedidoSalvo = pedidoService.salvar(pedidoMok);

        assertEquals(pedidoMok.getCep(), pedidoSalvo.getCep());
        assertNotNull(pedidoSalvo.getCep());
    }


}
