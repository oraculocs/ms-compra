package com.mscompra.compra.service;

import com.mscompra.compra.model.Pedido;
import com.mscompra.compra.repository.PedidoRepository;
import com.mscompra.compra.service.exception.NegocioException;
import com.mscompra.compra.service.rabbitmq.Producer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @DisplayName("Deve falhar na busca de pedido que não existe")
    @Test
    void deveFalharNaBusca(){
        var id = 1L;

        Throwable exception = assertThrows(NegocioException.class, () ->
            pedidoService.buscarOuFalharPorId(id));

        assertEquals("O pedido de id: " + id + " " +
                "não existe na base de dados" , exception.getMessage());
    }

    @DisplayName("Deve buscar um pedido com sucesso na base de dados")
    @Test
    void deveBuscarPedidoComSucesso(){
        var pedidoMok = mock.getPedidoSalvo();
        var id = 1L;

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMok));

        var pedidoSalvo = pedidoService.buscarOuFalharPorId(id);

        assertEquals(pedidoMok.getId(), pedidoSalvo.getId());
        assertNotNull(pedidoSalvo);
        Mockito.verify(pedidoRepository, Mockito.atLeastOnce()).findById(id);
    }

    @DisplayName("Deve excluir o pedido com sucesso")
    @Test
    void deveExcluirPedidoComSucesso(){
        var pedidoMok = mock.getPedidoSalvo();
        var id = 1L;

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMok));
        Mockito.doNothing().when(pedidoRepository).deleteById(id);

        pedidoService.excluir(id);

        Mockito.verify(pedidoRepository, Mockito.atLeastOnce()).deleteById(id);
    }

    @DisplayName("Deve falhar ao excluir o pedido que não existe")
    @Test
    void deveFalharAoExcluirPedido(){
        var id = 1L;

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(NegocioException.class, () ->
            pedidoService.excluir(id));

        assertEquals("O pedido de id: " + id + " " +
                "não existe na base de dados" , exception.getMessage());

    }

}
