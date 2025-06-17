package com.tech_nova.tech_nova.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.Direccion;
import com.tech_nova.tech_nova.model.Usuario;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long>{

    List<Direccion> findByCiudad(String ciudad);

    List<Direccion> findByRegion(String region);

    List<Direccion> findByCodigoPostal(String codigoPostal);

    List<Direccion> findByUsuario(Usuario usuario);

}
