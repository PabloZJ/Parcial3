package com.tech_nova.tech_nova.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.TipoPago;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long>{
    
    List<Boleta> findByPedido(Pedido pedido);

    List<Boleta> findByTipoPago(TipoPago tipoPago);

    List<Boleta> findByFechaEmision(Date fechaEmision);

}
