package com.tech_nova.tech_nova.model;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String descripcion;

    @Column(nullable=false)
    private int precio;
    
    @Column(nullable=false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "idProductoCategoria", nullable = false)
    private ProductoCategoria productoCategoria;

}
