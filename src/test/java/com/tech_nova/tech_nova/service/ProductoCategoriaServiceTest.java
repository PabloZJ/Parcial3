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

import com.tech_nova.tech_nova.model.ProductoCategoria;
import com.tech_nova.tech_nova.repository.ProductoCategoriaRepository;


@SpringBootTest
public class ProductoCategoriaServiceTest {
    
    @Autowired
    private ProductoCategoriaService productoCategoriaService;

    @MockBean
    ProductoCategoriaRepository productoCategoriaRepository;
//------------------------------------------------------
    private ProductoCategoria createProductoCategoria(){
        return new ProductoCategoria(
            1,
            "Hitler",
            "De nazi"
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(productoCategoriaRepository.findAll()).thenReturn(List.of(createProductoCategoria()));
        List<ProductoCategoria> productoCategorias = productoCategoriaRepository.findAll();
        assertNotNull(productoCategorias);
        assertEquals(1,productoCategorias.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(productoCategoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(createProductoCategoria()));
        ProductoCategoria productoCategoria = productoCategoriaService.obtenerProductoCategoriaPorId(1L);
        assertNotNull(productoCategoria);
        assertEquals("Hitler", productoCategoria.getNombre());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        ProductoCategoria productoCategoria = createProductoCategoria();
        when(productoCategoriaRepository.save(productoCategoria)).thenReturn(productoCategoria);
        ProductoCategoria productoCategoriaGuardado = productoCategoriaService.guardarProductoCategoria(productoCategoria);
        assertNotNull(productoCategoriaGuardado);
        assertEquals(1, productoCategoriaGuardado.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(productoCategoriaRepository).deleteById(1L);
        productoCategoriaService.eliminarProductoCategoria(1L);
        verify(productoCategoriaRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        ProductoCategoria productoCategoriaExistente = createProductoCategoria();
        ProductoCategoria pathData = new ProductoCategoria();
        pathData.setNombre("Hitler actualizado");

        when(productoCategoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(productoCategoriaExistente));
        when(productoCategoriaRepository.save(any(ProductoCategoria.class))).thenReturn(productoCategoriaExistente);

        ProductoCategoria productoCategoriaActualizado = productoCategoriaService.actualizarProductoCategoria(1L, pathData);
        assertNotNull(productoCategoriaActualizado);
        assertEquals("Hitler actualizado", productoCategoriaActualizado.getNombre());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        ProductoCategoria productoCategoriaExistente = createProductoCategoria();
        ProductoCategoria pathData = new ProductoCategoria();
        pathData.setNombre("Hitler actualizado");

        when(productoCategoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(productoCategoriaExistente));
        when(productoCategoriaRepository.save(any(ProductoCategoria.class))).thenReturn(productoCategoriaExistente);

        ProductoCategoria productoCategoriaActualizado = productoCategoriaService.actualizarProductoCategoriaParcial(1L, pathData);
        assertNotNull(productoCategoriaActualizado);
        assertEquals("Hitler actualizado", productoCategoriaActualizado.getNombre());
    }
//------------------------------------------------------
}
