package com.tech_nova.tech_nova.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.EstadoPedido;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Usuario;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    List<Pedido> findByUsuario(Usuario usuario);
    List<Pedido> findByEstadoPedido(EstadoPedido estadoPedido);
}
