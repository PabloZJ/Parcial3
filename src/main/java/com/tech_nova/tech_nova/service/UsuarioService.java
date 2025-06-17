package com.tech_nova.tech_nova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Obtener usuarios
    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    //Obtener usuario por id
    public Usuario obtenerUsuarioPorId (Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    //Guardar usuario
    public Usuario guardarUsuario (Usuario usuario){
        return usuarioRepository.save(usuario);

    }
    //Eliminar usuario
    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
    //ActualizarTodo
    public Usuario actualizarUsuario(Long id, Usuario usuario){
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null){
            usuarioExistente.setNombreCompleto(usuario.getNombreCompleto());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setContrasena(usuario.getContrasena());
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioExistente.setTelefono(usuario.getTelefono());
            return usuarioRepository.save(usuarioExistente);
        }
        else{
            return null;
        }
    }
    //ActualizarPath
    public Usuario actualizarUsuarioParcial(Long id, Usuario usuario){
    Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
    
    if (usuarioExistente != null){
        if(usuario.getNombreCompleto() != null)
            usuarioExistente.setNombreCompleto(usuario.getNombreCompleto());
        if(usuario.getCorreo() != null)
            usuarioExistente.setCorreo(usuario.getCorreo());
        if(usuario.getContrasena() != null)
            usuarioExistente.setContrasena(usuario.getContrasena());
        if(usuario.getFechaNacimiento() != null)
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        if(usuario.getTelefono() != null)
            usuarioExistente.setTelefono(usuario.getTelefono());
        return usuarioRepository.save(usuarioExistente);
    }
    else{
        return null;
    }
    }
}
