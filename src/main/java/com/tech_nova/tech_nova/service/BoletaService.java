package com.tech_nova.tech_nova.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.TipoPago;
import com.tech_nova.tech_nova.repository.BoletaRepository;
import com.tech_nova.tech_nova.repository.PedidoRepository;
import com.tech_nova.tech_nova.repository.TipoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoletaService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private BoletaRepository boletaRepository;

//Obtener lista boletas
    public List<Boleta> obtenerBoletas() {
        return boletaRepository.findAll();
    }
//Obtener boleta por id
    public Boleta obtenerBoletaPorId(Long id) {
        return boletaRepository.findById(id).orElse(null);
    }
//Obtener boleta por pedido
    public List<Boleta> obtenerBoletasPorPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null) {
            return boletaRepository.findByPedido(pedido);
        }
        return List.of();
    }    
//Obtener boletas por tipo de pagos
    public List<Boleta> obtenerBoletasPorEstado(Long estadoId) {
        TipoPago estado = tipoPagoRepository.findById(estadoId).orElse(null);
        if (estado != null) {
            return boletaRepository.findByTipoPago(estado);
        }
        return List.of();
    }
//Guardar boleta 
    public Boleta guardarBoleta(Boleta boleta) {
        if (boleta.getTipoPago() != null && boleta.getTipoPago().getId() != null) {
            Long tipoPagoId = ((Number) boleta.getTipoPago().getId()).longValue();
            TipoPago estado = tipoPagoRepository.findById(tipoPagoId)
                    .orElse(null);
            boleta.setTipoPago(estado);
        }
        if (boleta.getPedido() != null && boleta.getPedido().getId() != null) {
            Long pedidoId = ((Number) boleta.getPedido().getId()).longValue();
            Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
            boleta.setPedido(pedido);
        }
        return boletaRepository.save(boleta);
    }
//Eliminar 
    public void eliminarBoleta(Long id) {
        boletaRepository.deleteById(id);
    }
//ActualizarTodo
    public Boleta actualizarBoleta(Long id, Boleta boleta) {
        Boleta boletaExistente = boletaRepository.findById(id).orElse(null);
        if (boletaExistente != null) {
            if (boleta.getTipoPago() != null && boleta.getTipoPago().getId() != null) {
            Long tipoPagoId = ((Number) boleta.getTipoPago().getId()).longValue();
            TipoPago tipoPago = tipoPagoRepository.findById(tipoPagoId).orElseThrow();
            boletaExistente.setTipoPago(tipoPago);
            }
            if (boleta.getPedido() != null && boleta.getPedido().getId() != null) {
            Long pedidoId = ((Number) boleta.getPedido().getId()).longValue();
            Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
            boletaExistente.setPedido(pedido);
            }
            boletaExistente.setTotalMonto(boleta.getTotalMonto());
            boletaExistente.setFechaEmision(boleta.getFechaEmision());
            return boletaRepository.save(boletaExistente);
        } else {
            return null;
        }
    }
//ActualizarPath
    public Boleta actualizarBoletaParcial(Long id, Boleta boleta) {
        Boleta boletaExistente = boletaRepository.findById(id).orElse(null);
        if (boletaExistente != null) {
            if (boleta.getTipoPago() != null && boleta.getTipoPago().getId() != null) {
            Long tipoPagoId = ((Number) boleta.getTipoPago().getId()).longValue();
            TipoPago tipoPago = tipoPagoRepository.findById(tipoPagoId).orElseThrow();
            boletaExistente.setTipoPago(tipoPago);
            }
            if (boleta.getPedido() != null && boleta.getPedido().getId() != null) {
            Long pedidoId = ((Number) boleta.getPedido().getId()).longValue();
            Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
            boletaExistente.setPedido(pedido);
            }
            if (boleta.getTotalMonto() != 0){
                boletaExistente.setTotalMonto(boleta.getTotalMonto());
            }
            if (boleta.getFechaEmision() != null){
                boletaExistente.setFechaEmision(boleta.getFechaEmision());
            }
            return boletaRepository.save(boletaExistente);
        } else {
            return null;
        }
    }
}
