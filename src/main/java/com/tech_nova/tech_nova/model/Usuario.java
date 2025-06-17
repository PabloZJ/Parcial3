package com.tech_nova.tech_nova.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 50, nullable=false)
    private String nombreCompleto;
    
    @Column(unique = true, length = 30, nullable=false)
    private String correo;
    
    @Column(nullable = false, length = 20)
    private String contrasena;
    
    @Column(nullable = false)
    private Date fechaNacimiento;
    
    @Column(nullable = true)
    private String telefono;
    
}
