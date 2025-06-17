package com.tech_nova.tech_nova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.Usuario;
import java.util.List;
import java.util.Date;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByCorreo(String correo);
    List<Usuario> findByNombreCompleto(String nombreCompleto);
    List<Usuario> findByFechaNacimiento(Date fechaNacimiento);

}
