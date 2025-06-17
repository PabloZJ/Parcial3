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

import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.service.DetallePedidoService;

@RestController
@RequestMapping("/api/v1/detalle-producto")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedido>> listar() {
        List<DetallePedido> detallePedidos = detallePedidoService.obtenerDetallePedidos();
        if(detallePedidos.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detallePedidos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> buscarDetallePedidoPorId(@PathVariable Long id) {
        DetallePedido detallePedido = detallePedidoService.obtenerDetallePedidoPorId(id);
        if(detallePedido == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detallePedido);
    }
    @PostMapping
    public ResponseEntity<DetallePedido> guardar(@RequestBody DetallePedido detallePedido){
        DetallePedido nuevoDetallePedido = detallePedidoService.guardarDetallePedido(detallePedido);
        return ResponseEntity.status(200).body(nuevoDetallePedido);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Long id, @RequestBody DetallePedido detallePedido){
        DetallePedido detallePedidoActualizado = detallePedidoService.actualizarDetallePedido(id, detallePedido);
        if (detallePedidoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detallePedidoActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<DetallePedido> editar(@PathVariable Long id, @RequestBody DetallePedido detallePedido){
        DetallePedido detallePedidoActualizado = detallePedidoService.actualizarDetallePedidoParcial(id, detallePedido);
        if (detallePedidoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detallePedidoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        detallePedidoService.eliminarDetallePedido(id);
        return ResponseEntity.noContent().build();
    }

}
