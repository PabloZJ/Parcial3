package com.tech_nova.tech_nova.model;

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
@Table(name = "direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable=false)
    private String calle;

    @Column(length = 50, nullable=false)
    private String numero;

    @Column(length = 50, nullable=true)
    private String departamento;

    @Column(length = 50, nullable=false)
    private String ciudad;
    
    @Column(length = 50, nullable=false)
    private String region;

    @Column(nullable=false)
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;        
}
