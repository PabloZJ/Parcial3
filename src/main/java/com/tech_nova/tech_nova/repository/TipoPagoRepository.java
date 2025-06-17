package com.tech_nova.tech_nova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.TipoPago;

@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Long> {

    TipoPago findByNombre(String nombre);
}
