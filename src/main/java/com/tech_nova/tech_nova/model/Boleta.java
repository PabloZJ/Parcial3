package com.tech_nova.tech_nova.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "boleta")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false) 
    private Integer totalMonto;

    @Column(nullable = false)
    private Date fechaEmision;

    @ManyToOne
    @JoinColumn(name = "idTipoPago", nullable = false)
    private TipoPago tipoPago;

    @ManyToOne
    @JoinColumn(name = "idPedido", nullable= false)
    private Pedido pedido;
}
