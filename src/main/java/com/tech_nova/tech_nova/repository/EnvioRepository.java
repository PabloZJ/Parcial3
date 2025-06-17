package com.tech_nova.tech_nova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    Envio findByDescripcion(String descripcion);

}
