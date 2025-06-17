package com.tech_nova.tech_nova.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech_nova.tech_nova.model.Envio;
import com.tech_nova.tech_nova.service.EnvioService;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {
    
    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listar() {
        List<Envio> envios = envioService.obtenerEnvios();
        if(envios.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarenvioPorId(@PathVariable Long id) {
        Envio envio = envioService.obtenerEnvioPorId(id);
        if(envio == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envio);
    }
    @PostMapping
    public ResponseEntity<Envio> guardar(@RequestBody Envio envio){
        Envio nuevoEnvio = envioService.guardarEnvio(envio);
        return ResponseEntity.status(200).body(nuevoEnvio);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable Long id, @RequestBody Envio envio){
        Envio envioActualizado = envioService.actualizarEnvio(id, envio);
        if (envioActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envioActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Envio> editar(@PathVariable Long id, @RequestBody Envio envio){
        Envio envioActualizado = envioService.actualizarEnvioParcial(id, envio);
        if (envioActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envioActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        envioService.eliminarEnvio(id);
        return ResponseEntity.noContent().build();
    }
}
