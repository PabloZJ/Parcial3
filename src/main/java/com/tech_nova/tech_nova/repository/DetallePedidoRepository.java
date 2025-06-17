package com.tech_nova.tech_nova.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Producto;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long>{

    List<DetallePedido> findByPedido(Pedido pedido);

    List<DetallePedido> findByProducto(Producto producto);

    List<DetallePedido> findByProductoAndPedido(Producto producto, Pedido pedido);
}
