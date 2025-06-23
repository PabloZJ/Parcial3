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

import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.model.ProductoCategoria;
import com.tech_nova.tech_nova.repository.ProductoRepository;

@SpringBootTest
public class ProductoServiceTest {
    
    @Autowired
    private ProductoService productoService;

    @MockBean
    ProductoRepository productoRepository;
//------------------------------------------------------
    private Producto createProducto(){
        return new Producto(
            1,
            "Adolf",
            "Hola",
            100,
            10,
            new ProductoCategoria()
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(productoRepository.findAll()).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoRepository.findAll();
        assertNotNull(productos);
        assertEquals(1,productos.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(createProducto()));
        Producto producto = productoService.obtenerProductoPorId(1L);
        assertNotNull(producto);
        assertEquals("Hola", producto.getDescripcion());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        Producto producto = createProducto();
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto productoGuardado = productoService.guardarProductoBee(producto);
        assertNotNull(productoGuardado);
        assertEquals(1, productoGuardado.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(productoRepository).deleteById(1L);
        productoService.eliminarProducto(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        Producto productoExistente = createProducto();
        Producto pathData = new Producto();
        pathData.setDescripcion("Hola actualizado");

        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoExistente);

        Producto productoActualizado = productoService.actualizarProducto(1L, pathData);
        assertNotNull(productoActualizado);
        assertEquals("Hola actualizado", productoActualizado.getDescripcion());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        Producto productoExistente = createProducto();
        Producto pathData = new Producto();
        pathData.setDescripcion("Hola actualizado");

        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoExistente);

        Producto productoActualizado = productoService.actualizarProductoParcial(1L, pathData);
        assertNotNull(productoActualizado);
        assertEquals("Hola actualizado", productoActualizado.getDescripcion());
    }
//------------------------------------------------------
}
