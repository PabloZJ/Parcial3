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

import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.obtenerPedidos();
        if(pedidos.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        if(pedido == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedido);
    }
    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido){
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
        return ResponseEntity.status(200).body(nuevoPedido);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody Pedido pedido){
        Pedido pedidoActualizado = pedidoService.actualizarPedido(id, pedido);
        if (pedidoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidoActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Pedido> editar(@PathVariable Long id, @RequestBody Pedido pedido){
        Pedido pedidoActualizado = pedidoService.actualizarPedidoParcial(id, pedido);
        if (pedidoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
