package com.tech_nova.tech_nova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.repository.DetallePedidoRepository;
import com.tech_nova.tech_nova.repository.PedidoRepository;
import com.tech_nova.tech_nova.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetallePedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

//Obtener lista detalle de pedidos
    public List<DetallePedido> obtenerDetallePedidos() {
        return detallePedidoRepository.findAll();
    }
//Obtener detalle de pedido por id
    public DetallePedido obtenerDetallePedidoPorId(Long id) {
        return detallePedidoRepository.findById(id).orElse(null);
    }
//Obtener detalle de pedido por pedido
    public List<DetallePedido> obtenerDetallePedidosPorPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null) {
            return detallePedidoRepository.findByPedido(pedido);
        }
        return List.of();
    }    

    public DetallePedido guardarDetallePedido(DetallePedido detallePedido) {
        if (detallePedido.getProducto() != null && detallePedido.getProducto().getId() != null) {
            Long productoId = ((Number) detallePedido.getProducto().getId()).longValue();
            Producto estado = productoRepository.findById(productoId)
                    .orElse(null);
            detallePedido.setProducto(estado);
        }
        if (detallePedido.getPedido() != null && detallePedido.getPedido().getId() != null) {
            Long pedidoId = ((Number) detallePedido.getPedido().getId()).longValue();
            Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
            detallePedido.setPedido(pedido);
        }
        return detallePedidoRepository.save(detallePedido);
    }
//Eliminar 
    public void eliminarDetallePedido(Long id) {
        detallePedidoRepository.deleteById(id);
    }
//ActualizarTodo
    public DetallePedido actualizarDetallePedido(Long id, DetallePedido detallePedido) {
        DetallePedido detallePedidoExistente = detallePedidoRepository.findById(id).orElse(null);
        if (detallePedidoExistente != null) {
            if (detallePedido.getProducto() != null && detallePedido.getProducto().getId() != null) {
            Long productoId = ((Number) detallePedido.getProducto().getId()).longValue();
            Producto producto = productoRepository.findById(productoId).orElseThrow();
            detallePedidoExistente.setProducto(producto);
            }
            if (detallePedido.getPedido() != null && detallePedido.getPedido().getId() != null) {
            Long pedidoId = ((Number) detallePedido.getPedido().getId()).longValue();
            Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
            detallePedidoExistente.setPedido(pedido);
            }
            detallePedidoExistente.setCantidadProducto(detallePedido.getCantidadProducto());
            return detallePedidoRepository.save(detallePedidoExistente);
        } else {
            return null;
        }
    }
//ActualizarPath
    public DetallePedido actualizarDetallePedidoParcial(Long id, DetallePedido detallePedido) {
        DetallePedido detallePedidoExistente = detallePedidoRepository.findById(id).orElse(null);
        if (detallePedidoExistente != null) {
            if (detallePedido.getPedido() != null && detallePedido.getPedido().getId() != null) {
            Long pedidoId = ((Number) detallePedido.getPedido().getId()).longValue();
            Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
            detallePedidoExistente.setPedido(pedido);
            }
            if (detallePedido.getProducto() != null && detallePedido.getProducto().getId() != null) {
            Long pedidoId = ((Number) detallePedido.getProducto().getId()).longValue();
            Producto productoId = productoRepository.findById(pedidoId).orElseThrow();
            detallePedidoExistente.setProducto(productoId);
            }
            if (detallePedido.getCantidadProducto() != null){
                detallePedidoExistente.setCantidadProducto(detallePedido.getCantidadProducto());
            }
            return detallePedidoRepository.save(detallePedidoExistente);
        } else {
            return null;
        }
    }
}
