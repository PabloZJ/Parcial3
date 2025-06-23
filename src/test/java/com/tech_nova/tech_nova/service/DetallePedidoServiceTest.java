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

import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.repository.DetallePedidoRepository;


@SpringBootTest
public class DetallePedidoServiceTest {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @MockBean
    DetallePedidoRepository detallePedidoRepository;
//------------------------------------------------------
    private DetallePedido createDetallePedido(){
        return new DetallePedido(
            1,
            1,
            new Producto(),
            new Pedido()
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(detallePedidoRepository.findAll()).thenReturn(List.of(createDetallePedido()));
        List<DetallePedido> detallePedidos = detallePedidoRepository.findAll();
        assertNotNull(detallePedidos);
        assertEquals(1,detallePedidos.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(detallePedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(createDetallePedido()));
        DetallePedido detallePedido = detallePedidoService.obtenerDetallePedidoPorId(1L);
        assertNotNull(detallePedido);
        assertEquals(1, detallePedido.getCantidadProducto());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        DetallePedido detallePedido = createDetallePedido();
        when(detallePedidoRepository.save(detallePedido)).thenReturn(detallePedido);
        DetallePedido detallePedidoGuardada = detallePedidoService.guardarDetallePedido(detallePedido);
        assertNotNull(detallePedidoGuardada);
        assertEquals(1, detallePedidoGuardada.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(detallePedidoRepository).deleteById(1L);
        detallePedidoService.eliminarDetallePedido(1L);
        verify(detallePedidoRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        DetallePedido detallePedidoExistente = createDetallePedido();
        DetallePedido pathData = new DetallePedido();
        pathData.setCantidadProducto(2);

        when(detallePedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(detallePedidoExistente));
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(detallePedidoExistente);

        DetallePedido detallePedidoActualizada = detallePedidoService.actualizarDetallePedido(1L, pathData);
        assertNotNull(detallePedidoActualizada);
        assertEquals(2, detallePedidoActualizada.getCantidadProducto());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        DetallePedido detallePedidoExistente = createDetallePedido();
        DetallePedido pathData = new DetallePedido();
        pathData.setCantidadProducto(2);

        when(detallePedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(detallePedidoExistente));
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(detallePedidoExistente);

        DetallePedido detallePedidoActualizada = detallePedidoService.actualizarDetallePedidoParcial(1L, pathData);
        assertNotNull(detallePedidoActualizada);
        assertEquals(2, detallePedidoActualizada.getCantidadProducto());
    }
//------------------------------------------------------
}
