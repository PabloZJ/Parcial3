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

import com.tech_nova.tech_nova.model.EstadoPedido;
import com.tech_nova.tech_nova.service.EstadoPedidoService;

@RestController
@RequestMapping("/api/v1/estado-pedidos")
public class EstadoPedidoController {
    
    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @GetMapping
    public ResponseEntity<List<EstadoPedido>> listar() {
        List<EstadoPedido> estadoPedidos = estadoPedidoService.obtenerEstadoPedidos();
        if(estadoPedidos.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estadoPedidos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EstadoPedido> buscarestadoPedidoPorId(@PathVariable Long id) {
        EstadoPedido estadoPedido = estadoPedidoService.obtenerEstadoPedidoPorId(id);
        if(estadoPedido == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estadoPedido);
    }
    @PostMapping
    public ResponseEntity<EstadoPedido> guardar(@RequestBody EstadoPedido estadoPedido){
        EstadoPedido nuevoEstadoPedido = estadoPedidoService.guardarEstadoPedido(estadoPedido);
        return ResponseEntity.status(200).body(nuevoEstadoPedido);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EstadoPedido> actualizar(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido){
        EstadoPedido estadoPedidoActualizado = estadoPedidoService.actualizarEstadoPedido(id, estadoPedido);
        if (estadoPedidoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estadoPedidoActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<EstadoPedido> editar(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido){
        EstadoPedido estadoPedidoActualizado = estadoPedidoService.actualizarEstadoPedidoParcial(id, estadoPedido);
        if (estadoPedidoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estadoPedidoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        estadoPedidoService.eliminarEstadoPedido(id);
        return ResponseEntity.noContent().build();
    }
}
