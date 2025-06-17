package com.tech_nova.tech_nova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Direccion;
import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.DireccionRepository;
import com.tech_nova.tech_nova.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

//Obtener lista direcciones
    public List<Direccion> obtenerDirecciones() {
        return direccionRepository.findAll();
    }
//Obtener direccion por id
    public Direccion obtenerDireccionPorId(Long id) {
        return direccionRepository.findById(id).orElse(null);
    }
//Obtener direccion por usuario
    public List<Direccion> obtenerDireccionsPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario != null) {
            return direccionRepository.findByUsuario(usuario);
        }
        return List.of();
    }    
//Guardar direccion 
    public Direccion guardarDireccion(Direccion direccion) {
        if (direccion.getUsuario() != null && direccion.getUsuario().getId() != null) {
            Long usuarioId = ((Number) direccion.getUsuario().getId()).longValue();
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            direccion.setUsuario(usuario);
        }
        return direccionRepository.save(direccion);
    }
//Eliminar 
    public void eliminarDireccion(Long id) {
        direccionRepository.deleteById(id);
    }
//ActualizarTodo
    public Direccion actualizarDireccion(Long id, Direccion direccion) {
        Direccion direccionExistente = direccionRepository.findById(id).orElse(null);
        if (direccionExistente != null) {
            if (direccion.getUsuario() != null && direccion.getUsuario().getId() != null) {
            Long usuarioId = ((Number) direccion.getUsuario().getId()).longValue();
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            direccionExistente.setUsuario(usuario);
            }
            direccionExistente.setCalle(direccion.getCalle());
            direccionExistente.setNumero(direccion.getNumero());
            direccionExistente.setDepartamento(direccion.getDepartamento());
            direccionExistente.setCiudad(direccion.getCiudad());
            direccionExistente.setRegion(direccion.getRegion());
            direccionExistente.setCodigoPostal(direccion.getCodigoPostal());
            return direccionRepository.save(direccionExistente);
        } else {
            return null;
        }
    }
//ActualizarPath
    public Direccion actualizarDireccionParcial(Long id, Direccion direccion) {
        Direccion direccionExistente = direccionRepository.findById(id).orElse(null);
        if (direccionExistente != null) {
            if (direccion.getUsuario() != null && direccion.getUsuario().getId() != null) {
            Long usuarioId = ((Number) direccion.getUsuario().getId()).longValue();
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            direccionExistente.setUsuario(usuario);
            }
            if (direccion.getCalle() != null){
                direccionExistente.setCalle(direccion.getCalle());
            }
            if (direccion.getNumero() != null){
                direccionExistente.setNumero(direccion.getNumero());
            }
            if (direccion.getDepartamento()!= null){
                direccionExistente.setDepartamento(direccion.getDepartamento());
            }
            if (direccion.getCiudad() != null){
                direccionExistente.setCiudad(direccion.getCiudad());
            }
            if (direccion.getRegion() != null){
                direccionExistente.setRegion(direccion.getRegion());
            }
            if (direccion.getCodigoPostal() != null){
                direccionExistente.setCodigoPostal(direccion.getCodigoPostal());
            }
                return direccionRepository.save(direccionExistente);
        } else {
            return null;
        }
    }
}
