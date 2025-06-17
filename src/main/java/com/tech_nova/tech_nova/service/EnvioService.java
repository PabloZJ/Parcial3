package com.tech_nova.tech_nova.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Envio;
import com.tech_nova.tech_nova.repository.EnvioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EnvioService {
    
    
    @Autowired
    private EnvioRepository envioRepository;

    //Obtener
    public List<Envio> obtenerEnvios(){
        return envioRepository.findAll();
    }

    //Obtener id
    public Envio obtenerEnvioPorId (Long id){
        return envioRepository.findById(id).orElse(null);
    }

    //Guardar
    public Envio guardarEnvio (Envio envio){
        return envioRepository.save(envio);

    }
    //Eliminar
    public void eliminarEnvio(Long id){
        envioRepository.deleteById(id);
    }
    //ActualizarTodo
    public Envio actualizarEnvio(Long id, Envio envio){
        Envio envioExistente = envioRepository.findById(id).orElse(null);
        if (envioExistente != null){
            envioExistente.setDescripcion(envio.getDescripcion());
            return envioRepository.save(envioExistente);
        }
        else{
            return null;
        }
    }
    //ActualizarPath
    public Envio actualizarEnvioParcial(Long id, Envio Envio){
    Envio envioExistente = envioRepository.findById(id).orElse(null);
    if (envioExistente != null){
        if(Envio.getDescripcion() != null)
            envioExistente.setDescripcion(Envio.getDescripcion());
        return envioRepository.save(envioExistente);
    }
    else{
        return null;
    }
    }
}
