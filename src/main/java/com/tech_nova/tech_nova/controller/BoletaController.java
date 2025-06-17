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

import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.service.BoletaService;


@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {
    
    @Autowired
    private BoletaService boletaService;

    @GetMapping
    public ResponseEntity<List<Boleta>> listar() {
        List<Boleta> boletas = boletaService.obtenerBoletas();
        if(boletas.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Boleta> buscarBoletaPorId(@PathVariable Long id) {
        Boleta boleta = boletaService.obtenerBoletaPorId(id);
        if(boleta == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boleta);
    }
    @PostMapping
    public ResponseEntity<Boleta> guardar(@RequestBody Boleta boleta){
        Boleta nuevoBoleta = boletaService.guardarBoleta(boleta);
        return ResponseEntity.status(200).body(nuevoBoleta);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boleta> actualizar(@PathVariable Long id, @RequestBody Boleta boleta){
        Boleta boletaActualizado = boletaService.actualizarBoleta(id, boleta);
        if (boletaActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boletaActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Boleta> editar(@PathVariable Long id, @RequestBody Boleta boleta){
        Boleta boletaActualizado = boletaService.actualizarBoletaParcial(id, boleta);
        if (boletaActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boletaActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        boletaService.eliminarBoleta(id);
        return ResponseEntity.noContent().build();
    }

}
