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

import com.tech_nova.tech_nova.model.Direccion;
import com.tech_nova.tech_nova.service.DireccionService;


@RestController
@RequestMapping("/api/v1/direcciones")
public class DireccionController {
    
    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<Direccion>> listar() {
        List<Direccion> direccions = direccionService.obtenerDirecciones();
        if(direccions.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(direccions);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Direccion> buscarDireccionPorId(@PathVariable Long id) {
        Direccion direccion = direccionService.obtenerDireccionPorId(id);
        if(direccion == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(direccion);
    }
    @PostMapping
    public ResponseEntity<Direccion> guardar(@RequestBody Direccion direccion){
        Direccion nuevoDireccion = direccionService.guardarDireccion(direccion);
        return ResponseEntity.status(200).body(nuevoDireccion);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Direccion> actualizar(@PathVariable Long id, @RequestBody Direccion direccion){
        Direccion direccionActualizado = direccionService.actualizarDireccion(id, direccion);
        if (direccionActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(direccionActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Direccion> editar(@PathVariable Long id, @RequestBody Direccion direccion){
        Direccion direccionActualizado = direccionService.actualizarDireccionParcial(id, direccion);
        if (direccionActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(direccionActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }

}
