package com.tech_nova.tech_nova.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.TipoPago;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long>{
    
    List<Boleta> findByPedido(Pedido pedido);
    List<Boleta> findByTipoPago(TipoPago tipoPago);
    List<Boleta> findByFechaEmision(Date fechaEmision);

    @Query("SELECT b FROM Boleta b WHERE b.tipoPago.id = :idTipoPago")
    List<Boleta> findBoletasPorTipoPago(@Param("idTipoPago") Integer idTipoPago);

    @Query("SELECT b FROM Boleta b WHERE FUNCTION('date', b.fechaEmision) = :fecha")
    List<Boleta> findBoletasPorFecha(@Param("fecha") Date fecha);

    @Query("SELECT b FROM Boleta b JOIN b.pedido p JOIN p.usuario u WHERE u.id = :idUsuario")
    List<Boleta> findBoletasByUsuarioId(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT b FROM Boleta b JOIN b.pedido p JOIN p.usuario u WHERE FUNCTION('date', b.fechaEmision) = :fecha AND u.id = :idUsuario")
    List<Boleta> findBoletasByFechaAndUsuario(@Param("fecha") Date fecha, @Param("idUsuario") Integer idUsuario);

    @Query("SELECT b FROM Boleta b JOIN b.tipoPago tp WHERE tp.id = :idTipoPago AND b.totalMonto > :monto")
    List<Boleta> findBoletasByTipoPagoAndMontoMayor(@Param("idTipoPago") Integer idTipoPago, @Param("monto") Integer monto);

    @Query("SELECT b FROM Boleta b JOIN b.pedido p WHERE p.estadoPedido.id = 5")
    List<Boleta> findBoletasByEstadoPedidoEntregado();

}
