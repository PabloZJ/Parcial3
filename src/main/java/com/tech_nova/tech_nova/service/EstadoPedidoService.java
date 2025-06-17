package com.tech_nova.tech_nova.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.EstadoPedido;
import com.tech_nova.tech_nova.repository.EstadoPedidoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoPedidoService {
    
    
    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    //Obtener
    public List<EstadoPedido> obtenerEstadoPedidos(){
        return estadoPedidoRepository.findAll();
    }

    //Obtener id
    public EstadoPedido obtenerEstadoPedidoPorId (Long id){
        return estadoPedidoRepository.findById(id).orElse(null);
    }

    //Guardar
    public EstadoPedido guardarEstadoPedido (EstadoPedido estadoPedido){
        return estadoPedidoRepository.save(estadoPedido);

    }
    //Eliminar
    public void eliminarEstadoPedido(Long id){
        estadoPedidoRepository.deleteById(id);
    }
    //ActualizarTodo
    public EstadoPedido actualizarEstadoPedido(Long id, EstadoPedido estadoPedido){
        EstadoPedido estadoPedidoExistente = estadoPedidoRepository.findById(id).orElse(null);
        if (estadoPedidoExistente != null){
            estadoPedidoExistente.setNombre(estadoPedido.getNombre());
            return estadoPedidoRepository.save(estadoPedidoExistente);
        }
        else{
            return null;
        }
    }
    //ActualizarPath
    public EstadoPedido actualizarEstadoPedidoParcial(Long id, EstadoPedido EstadoPedido){
    EstadoPedido estadoPedidoExistente = estadoPedidoRepository.findById(id).orElse(null);
    if (estadoPedidoExistente != null){
        if(EstadoPedido.getNombre() != null)
            estadoPedidoExistente.setNombre(EstadoPedido.getNombre());
        return estadoPedidoRepository.save(estadoPedidoExistente);
    }
    else{
        return null;
    }
    }
}
