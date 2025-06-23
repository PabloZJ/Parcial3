package com.tech_nova.tech_nova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.model.EstadoPedido;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.BoletaRepository;
import com.tech_nova.tech_nova.repository.DetallePedidoRepository;
import com.tech_nova.tech_nova.repository.EstadoPedidoRepository;
import com.tech_nova.tech_nova.repository.PedidoRepository;
import com.tech_nova.tech_nova.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

//Obtener lista pedidos
    public List<Pedido> obtenerPedidos() {
        return pedidoRepository.findAll();
    }
//Obtener pedido por id
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }
//Obtener pedido por usuario
    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario != null) {
            return pedidoRepository.findByUsuario(usuario);
        }
        return List.of();
    }    
//Obtener pedidos por estado
    public List<Pedido> obtenerPedidosPorEstado(Long estadoId) {
        EstadoPedido estado = estadoPedidoRepository.findById(estadoId).orElse(null);
        if (estado != null) {
            return pedidoRepository.findByEstadoPedido(estado);
        }
        return List.of();
    }
//Guardar pedido 
    public Pedido guardarPedido(Pedido pedido) {
        if (pedido.getEstadoPedido() != null && pedido.getEstadoPedido().getId() != null) {
            Long estadoPedidoId = ((Number) pedido.getEstadoPedido().getId()).longValue();
            EstadoPedido estado = estadoPedidoRepository.findById(estadoPedidoId)
                    .orElse(null);
            pedido.setEstadoPedido(estado);
        }
        if (pedido.getUsuario() != null && pedido.getUsuario().getId() != null) {
            Long usuarioId = ((Number) pedido.getUsuario().getId()).longValue();
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            pedido.setUsuario(usuario);
        }
        return pedidoRepository.save(pedido);
    }
//Eliminar 
    public void eliminarPedido(Long id) {
    Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        List<Boleta> boletas = boletaRepository.findByPedido(pedido);
        for (Boleta boleta : boletas) {
            boletaRepository.delete(boleta);
        }
        List<DetallePedido> detalles = detallePedidoRepository.findByPedido(pedido);
        for (DetallePedido detalle : detalles) {
            detallePedidoRepository.delete(detalle);
        }
        pedidoRepository.delete(pedido);
    }
//ActualizarTodo
    public Pedido actualizarPedido(Long id, Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id).orElse(null);
        if (pedidoExistente != null) {
            if (pedido.getEstadoPedido() != null && pedido.getEstadoPedido().getId() != null) {
            Long estadoPedidoId = ((Number) pedido.getEstadoPedido().getId()).longValue();
            EstadoPedido estadoPedido = estadoPedidoRepository.findById(estadoPedidoId).orElseThrow();
            pedidoExistente.setEstadoPedido(estadoPedido);
            }
            if (pedido.getUsuario() != null && pedido.getUsuario().getId() != null) {
            Long usuarioId = ((Number) pedido.getUsuario().getId()).longValue();
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            pedidoExistente.setUsuario(usuario);
            }
            return pedidoRepository.save(pedidoExistente);
        } else {
            return null;
        }
    }
//ActualizarPath
    public Pedido actualizarPedidoParcial(Long id, Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id).orElse(null);
        if (pedidoExistente != null) {
            if (pedido.getEstadoPedido() != null && pedido.getEstadoPedido().getId() != null) {
            Long estadoPedidoId = ((Number) pedido.getEstadoPedido().getId()).longValue();
            EstadoPedido estadoPedido = estadoPedidoRepository.findById(estadoPedidoId).orElseThrow();
            pedidoExistente.setEstadoPedido(estadoPedido);
            }
            if (pedido.getUsuario() != null && pedido.getUsuario().getId() != null) {
            Long usuarioId = ((Number) pedido.getUsuario().getId()).longValue();
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            pedidoExistente.setUsuario(usuario);
            }
            return pedidoRepository.save(pedidoExistente);
        } else {
            return null;
        }
    }
}
