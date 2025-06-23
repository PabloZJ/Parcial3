package com.tech_nova.tech_nova.service;

import java.util.Date;
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

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.TipoPago;
import com.tech_nova.tech_nova.repository.BoletaRepository;


@SpringBootTest
public class BoletaServiceTest {

    @Autowired
    private BoletaService boletaService;

    @MockBean
    BoletaRepository boletaRepository;
//------------------------------------------------------
    private Boleta createBoleta(){
        return new Boleta(
            1,
            100,
            new Date(),
            new TipoPago(),
            new Pedido()
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(boletaRepository.findAll()).thenReturn(List.of(createBoleta()));
        List<Boleta> boletas = boletaRepository.findAll();
        assertNotNull(boletas);
        assertEquals(1,boletas.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(boletaRepository.findById(1L)).thenReturn(java.util.Optional.of(createBoleta()));
        Boleta boleta = boletaService.obtenerBoletaPorId(1L);
        assertNotNull(boleta);
        assertEquals(100, boleta.getTotalMonto());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        Boleta boleta = createBoleta();
        when(boletaRepository.save(boleta)).thenReturn(boleta);
        Boleta boletaGuardada = boletaService.guardarBoleta(boleta);
        assertNotNull(boletaGuardada);
        assertEquals(1, boletaGuardada.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(boletaRepository).deleteById(1L);
        boletaService.eliminarBoleta(1L);
        verify(boletaRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        Boleta boletaExistente = createBoleta();
        Boleta pathData = new Boleta();
        pathData.setTotalMonto(200);

        when(boletaRepository.findById(1L)).thenReturn(java.util.Optional.of(boletaExistente));
        when(boletaRepository.save(any(Boleta.class))).thenReturn(boletaExistente);

        Boleta boletaActualizada = boletaService.actualizarBoleta(1L, pathData);
        assertNotNull(boletaActualizada);
        assertEquals(200, boletaActualizada.getTotalMonto());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        Boleta boletaExistente = createBoleta();
        Boleta pathData = new Boleta();
        pathData.setTotalMonto(200);

        when(boletaRepository.findById(1L)).thenReturn(java.util.Optional.of(boletaExistente));
        when(boletaRepository.save(any(Boleta.class))).thenReturn(boletaExistente);

        Boleta boletaActualizada = boletaService.actualizarBoletaParcial(1L, pathData);
        assertNotNull(boletaActualizada);
        assertEquals(200, boletaActualizada.getTotalMonto());
    }
//------------------------------------------------------
    @Test
    public void testBuscarPorIdTipoPago() {
        when(boletaRepository.findBoletasPorTipoPago(1)).thenReturn(List.of(createBoleta()));
        List<Boleta> resultado = boletaService.getBoletasPorTipoPago(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
//------------------------------------------------------
    @Test
    public void testBuscarPorFecha() {
        Date fecha = new Date();
        when(boletaRepository.findBoletasPorFecha(fecha)).thenReturn(List.of(createBoleta()));
        List<Boleta> resultado = boletaService.getBoletasPorFecha(fecha);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
//------------------------------------------------------
    @Test
    public void testBuscarPorUsuarioId() {
        when(boletaRepository.findBoletasByUsuarioId(1)).thenReturn(List.of(createBoleta()));
        List<Boleta> resultado = boletaService.getBoletasPorUsuario(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
//------------------------------------------------------
    @Test
    public void testBuscarPorFechaYUsuario() {
        Date fecha = new Date();
        when(boletaRepository.findBoletasByFechaAndUsuario(fecha, 1)).thenReturn(List.of(createBoleta()));
        List<Boleta> resultado = boletaService.getBoletasPorFechaYUsuario(fecha, 1);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
//------------------------------------------------------
    @Test
    public void testBuscarPorTipoPagoYMontoMayor() {
        when(boletaRepository.findBoletasByTipoPagoAndMontoMayor(1, 50)).thenReturn(List.of(createBoleta()));
        List<Boleta> resultado = boletaService.getBoletasPorTipoPagoYMontoMayor(1, 50);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
//------------------------------------------------------
    @Test
    public void testBuscarBoletasEntregadas() {
        when(boletaRepository.findBoletasByEstadoPedidoEntregado()).thenReturn(List.of(createBoleta()));
        List<Boleta> resultado = boletaService.getBoletasConPedidoEntregado();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
//------------------------------------------------------
}
