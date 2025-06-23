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
import com.tech_nova.tech_nova.repository.EstadoPedidoRepository;


@SpringBootTest
public class EstadoPedidoServiceTest {
    
    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @MockBean
    EstadoPedidoRepository estadoPedidoRepository;
//------------------------------------------------------
    private EstadoPedido createEstadoPedido(){
        return new EstadoPedido(
            1,
            "Hola"
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(estadoPedidoRepository.findAll()).thenReturn(List.of(createEstadoPedido()));
        List<EstadoPedido> estadoPedidos = estadoPedidoRepository.findAll();
        assertNotNull(estadoPedidos);
        assertEquals(1,estadoPedidos.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(estadoPedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(createEstadoPedido()));
        EstadoPedido estadoPedido = estadoPedidoService.obtenerEstadoPedidoPorId(1L);
        assertNotNull(estadoPedido);
        assertEquals("Hola", estadoPedido.getNombre());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        EstadoPedido estadoPedido = createEstadoPedido();
        when(estadoPedidoRepository.save(estadoPedido)).thenReturn(estadoPedido);
        EstadoPedido estadoPedidoGuardado = estadoPedidoService.guardarEstadoPedido(estadoPedido);
        assertNotNull(estadoPedidoGuardado);
        assertEquals(1, estadoPedidoGuardado.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(estadoPedidoRepository).deleteById(1L);
        estadoPedidoService.eliminarEstadoPedido(1L);
        verify(estadoPedidoRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        EstadoPedido estadoPedidoExistente = createEstadoPedido();
        EstadoPedido pathData = new EstadoPedido();
        pathData.setNombre("Hola actualizado");

        when(estadoPedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(estadoPedidoExistente));
        when(estadoPedidoRepository.save(any(EstadoPedido.class))).thenReturn(estadoPedidoExistente);

        EstadoPedido estadoPedidoActualizado = estadoPedidoService.actualizarEstadoPedido(1L, pathData);
        assertNotNull(estadoPedidoActualizado);
        assertEquals("Hola actualizado", estadoPedidoActualizado.getNombre());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        EstadoPedido estadoPedidoExistente = createEstadoPedido();
        EstadoPedido pathData = new EstadoPedido();
        pathData.setNombre("Hola actualizado");

        when(estadoPedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(estadoPedidoExistente));
        when(estadoPedidoRepository.save(any(EstadoPedido.class))).thenReturn(estadoPedidoExistente);

        EstadoPedido estadoPedidoActualizado = estadoPedidoService.actualizarEstadoPedidoParcial(1L, pathData);
        assertNotNull(estadoPedidoActualizado);
        assertEquals("Hola actualizado", estadoPedidoActualizado.getNombre());
    }
//------------------------------------------------------
}
