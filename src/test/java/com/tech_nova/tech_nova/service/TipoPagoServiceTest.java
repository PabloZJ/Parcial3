package com.tech_nova.tech_nova.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tech_nova.tech_nova.model.TipoPago;
import com.tech_nova.tech_nova.repository.TipoPagoRepository;

@SpringBootTest
public class TipoPagoServiceTest {
    @Autowired
    private TipoPagoService tipoPagoService;

    @MockBean
    TipoPagoRepository tipoPagoRepository;
//------------------------------------------------------
    private TipoPago createTipoPago(){
        return new TipoPago(
            1,
            "Debito"
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(tipoPagoRepository.findAll()).thenReturn(List.of(createTipoPago()));
        List<TipoPago> tipoPagoes = tipoPagoRepository.findAll();
        assertNotNull(tipoPagoes);
        assertEquals(1,tipoPagoes.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(tipoPagoRepository.findById(1L)).thenReturn(java.util.Optional.of(createTipoPago()));
        TipoPago tipoPago = tipoPagoService.obtenerTipoPagoPorId(1L);
        assertNotNull(tipoPago);
        assertEquals("Debito", tipoPago.getNombre());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        TipoPago tipoPago = createTipoPago();
        when(tipoPagoRepository.save(tipoPago)).thenReturn(tipoPago);
        TipoPago tipoPagoGuardada = tipoPagoService.guardarTipoPago(tipoPago);
        assertNotNull(tipoPagoGuardada);
        assertEquals(1, tipoPagoGuardada.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(tipoPagoRepository).deleteById(1L);
        tipoPagoService.eliminarTipoPago(1L);
        verify(tipoPagoRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        TipoPago tipoPagoExistente = createTipoPago();
        TipoPago pathData = new TipoPago();
        pathData.setNombre("Debito actualizado");

        when(tipoPagoRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoPagoExistente));
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(tipoPagoExistente);

        TipoPago tipoPagoActualizada = tipoPagoService.actualizarTipoPago(1L, pathData);
        assertNotNull(tipoPagoActualizada);
        assertEquals("Debito actualizado", tipoPagoActualizada.getNombre());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        TipoPago tipoPagoExistente = createTipoPago();
        TipoPago pathData = new TipoPago();
        pathData.setNombre("Debito actualizado");

        when(tipoPagoRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoPagoExistente));
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(tipoPagoExistente);

        TipoPago tipoPagoActualizada = tipoPagoService.actualizarTipoPagoParcial(1L, pathData);
        assertNotNull(tipoPagoActualizada);
        assertEquals("Debito actualizado", tipoPagoActualizada.getNombre());
    }
//------------------------------------------------------
}
