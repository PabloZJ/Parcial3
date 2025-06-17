package com.tech_nova.tech_nova.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tech_nova.tech_nova.model.EstadoPedido;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.EstadoPedidoRepository;
import com.tech_nova.tech_nova.repository.PedidoRepository;

@SpringBootTest
public class PedidoServiceTest {
    
    @Autowired
    private PedidoService pedidoService;

    @MockBean
    PedidoRepository pedidoRepository;

    @MockBean
    EstadoPedidoRepository estadoPedidoRepository;
//------------------------------------------------------
    private EstadoPedido createEstadoPedido(){
        return new EstadoPedido(1, "omero chino");
    }

//------------------------------------------------------
    private Pedido createPedido(){
        return new Pedido(
            1,
            createEstadoPedido(),
            new Usuario()
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(pedidoRepository.findAll()).thenReturn(List.of(createPedido()));
        List<Pedido> pedidos = pedidoRepository.findAll();
        assertNotNull(pedidos);
        assertEquals(1,pedidos.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(createPedido()));
        Pedido pedido = pedidoService.obtenerPedidoPorId(1L);
        assertNotNull(pedido);
        assertEquals("omero chino", pedido.getEstadoPedido().getNombre());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        Pedido pedido = createPedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        Pedido pedidoGuardada = pedidoService.guardarPedido(pedido);
        assertNotNull(pedidoGuardada);
        assertEquals(1, pedidoGuardada.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(pedidoRepository).deleteById(1L);
        pedidoService.eliminarPedido(1L);
        verify(pedidoRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
@Test
public void testPut(){
    Pedido pedidoExistente = createPedido();
    EstadoPedido estadoPedidoNuevo = new EstadoPedido(1,"omero chino actualizado");
    Pedido pathData = new Pedido();
    pathData.setEstadoPedido(new EstadoPedido(1, null));

    when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(pedidoExistente));
    when(estadoPedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(estadoPedidoNuevo));
    when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoExistente);

    Pedido pedidoActualizada = pedidoService.actualizarPedido(1L, pathData);
    assertNotNull(pedidoActualizada);
    assertEquals("omero chino actualizado", pedidoActualizada.getEstadoPedido().getNombre());
}
//------------------------------------------------------
    @Test
    public void testPath(){
    Pedido pedidoExistente = createPedido();
    EstadoPedido estadoPedidoNuevo = new EstadoPedido(1,"omero chino actualizado");
    Pedido pathData = new Pedido();
    pathData.setEstadoPedido(new EstadoPedido(1, null));

    when(pedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(pedidoExistente));
    when(estadoPedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(estadoPedidoNuevo));
    when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoExistente);

    Pedido pedidoActualizada = pedidoService.actualizarPedidoParcial(1L, pathData);
    assertNotNull(pedidoActualizada);
    assertEquals("omero chino actualizado", pedidoActualizada.getEstadoPedido().getNombre());
    }
//------------------------------------------------------
}
