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

import com.tech_nova.tech_nova.model.Envio;
import com.tech_nova.tech_nova.repository.EnvioRepository;


@SpringBootTest
public class EnvioServiceTest {
    
    @Autowired
    private EnvioService envioService;

    @MockBean
    EnvioRepository envioRepository;
//------------------------------------------------------
    private Envio createEnvio(){
        return new Envio(
            1,
            "Hola"
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(envioRepository.findAll()).thenReturn(List.of(createEnvio()));
        List<Envio> envios = envioRepository.findAll();
        assertNotNull(envios);
        assertEquals(1,envios.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(envioRepository.findById(1L)).thenReturn(java.util.Optional.of(createEnvio()));
        Envio envio = envioService.obtenerEnvioPorId(1L);
        assertNotNull(envio);
        assertEquals("Hola", envio.getDescripcion());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        Envio envio = createEnvio();
        when(envioRepository.save(envio)).thenReturn(envio);
        Envio envioGuardado = envioService.guardarEnvio(envio);
        assertNotNull(envioGuardado);
        assertEquals(1, envioGuardado.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(envioRepository).deleteById(1L);
        envioService.eliminarEnvio(1L);
        verify(envioRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        Envio envioExistente = createEnvio();
        Envio pathData = new Envio();
        pathData.setDescripcion("Hola actualizado");

        when(envioRepository.findById(1L)).thenReturn(java.util.Optional.of(envioExistente));
        when(envioRepository.save(any(Envio.class))).thenReturn(envioExistente);

        Envio envioActualizado = envioService.actualizarEnvio(1L, pathData);
        assertNotNull(envioActualizado);
        assertEquals("Hola actualizado", envioActualizado.getDescripcion());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        Envio envioExistente = createEnvio();
        Envio pathData = new Envio();
        pathData.setDescripcion("Hola actualizado");

        when(envioRepository.findById(1L)).thenReturn(java.util.Optional.of(envioExistente));
        when(envioRepository.save(any(Envio.class))).thenReturn(envioExistente);

        Envio envioActualizado = envioService.actualizarEnvioParcial(1L, pathData);
        assertNotNull(envioActualizado);
        assertEquals("Hola actualizado", envioActualizado.getDescripcion());
    }
//------------------------------------------------------
}
