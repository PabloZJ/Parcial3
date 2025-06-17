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

import com.tech_nova.tech_nova.model.Direccion;
import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.DireccionRepository;


@SpringBootTest
public class DireccionServiceTest {

    @Autowired
    private DireccionService direccionService;

    @MockBean
    DireccionRepository direccionRepository;
//------------------------------------------------------
    private Direccion createDireccion(){
        return new Direccion(
            1,
            "Mi general",
            "10",
            "10",
            "Santiago", 
            "Metropolitana", 
            "09090909",
            new Usuario()
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(direccionRepository.findAll()).thenReturn(List.of(createDireccion()));
        List<Direccion> direcciones = direccionRepository.findAll();
        assertNotNull(direcciones);
        assertEquals(1,direcciones.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(direccionRepository.findById(1L)).thenReturn(java.util.Optional.of(createDireccion()));
        Direccion direccion = direccionService.obtenerDireccionPorId(1L);
        assertNotNull(direccion);
        assertEquals("Mi general", direccion.getCalle());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        Direccion direccion = createDireccion();
        when(direccionRepository.save(direccion)).thenReturn(direccion);
        Direccion direccionGuardada = direccionService.guardarDireccion(direccion);
        assertNotNull(direccionGuardada);
        assertEquals(1, direccionGuardada.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(direccionRepository).deleteById(1L);
        direccionService.eliminarDireccion(1L);
        verify(direccionRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        Direccion direccionExistente = createDireccion();
        Direccion pathData = new Direccion();
        pathData.setCalle("Mi general actualizado");

        when(direccionRepository.findById(1L)).thenReturn(java.util.Optional.of(direccionExistente));
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccionExistente);

        Direccion direccionActualizada = direccionService.actualizarDireccion(1L, pathData);
        assertNotNull(direccionActualizada);
        assertEquals("Mi general actualizado", direccionActualizada.getCalle());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        Direccion direccionExistente = createDireccion();
        Direccion pathData = new Direccion();
        pathData.setCalle("Mi general actualizado");

        when(direccionRepository.findById(1L)).thenReturn(java.util.Optional.of(direccionExistente));
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccionExistente);

        Direccion direccionActualizada = direccionService.actualizarDireccionParcial(1L, pathData);
        assertNotNull(direccionActualizada);
        assertEquals("Mi general actualizado", direccionActualizada.getCalle());
    }
//------------------------------------------------------
}
