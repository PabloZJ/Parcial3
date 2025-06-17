package com.tech_nova.tech_nova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.EstadoPedido;

@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Long>{
    //Encontrar el Estado por el nombre
    EstadoPedido findByNombre(String nombre);

}
