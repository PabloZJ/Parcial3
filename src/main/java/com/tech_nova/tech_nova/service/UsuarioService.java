package com.tech_nova.tech_nova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.model.Direccion;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.BoletaRepository;
import com.tech_nova.tech_nova.repository.DetallePedidoRepository;
import com.tech_nova.tech_nova.repository.DireccionRepository;
import com.tech_nova.tech_nova.repository.PedidoRepository;
import com.tech_nova.tech_nova.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

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
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

    List<Direccion> direcciones = direccionRepository.findByUsuario(usuario);
    //Borra las direcciones asociadas al usuario
    for (Direccion direccion : direcciones) {
        direccionRepository.delete(direccion);
    }

    List<Pedido> pedidos = pedidoRepository.findByUsuario(usuario);

    for (Pedido pedido : pedidos) {
        List<Boleta> boletas = boletaRepository.findByPedido(pedido);
        for (Boleta boleta : boletas) {
            boletaRepository.delete(boleta);
        }
        List<DetallePedido> detalles = detallePedidoRepository.findByPedido(pedido);
        for (DetallePedido detalle : detalles) {
            detallePedidoRepository.delete(detalle);
        }
        pedidoRepository.deleteById(Long.valueOf(pedido.getId()));
    }

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
