package com.tech_nova.tech_nova.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech_nova.tech_nova.assemblers.PedidoModelAssembler;
import com.tech_nova.tech_nova.model.Pedido;
import com.tech_nova.tech_nova.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/pedidos")
@Tag(name = "API que administra los pedidos")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    PedidoModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todos los pedidos", description = "Está API se encarga de llamar todas los pedidos existentes en la base de datos")
    public CollectionModel<EntityModel<Pedido>> getAllPedidos(){
        List<EntityModel<Pedido>> pedidos = pedidoService.obtenerPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withRel("Pedidos"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene un pedido por id ", description = "Está API se encarga de llamar un pedido por su id especifico")
    public ResponseEntity<EntityModel<Pedido>> getPedidoById(@PathVariable Long id){
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        if (pedido == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(pedido));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea un pedido", description = "está API se encarga de crear un pedido")
    public ResponseEntity<EntityModel<Pedido>> createPedido(@RequestBody Pedido pedido){
        Pedido nuevaPedido = pedidoService.guardarPedido(pedido);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoControllerV2.class).getPedidoById(Long.valueOf(nuevaPedido.getId()))).toUri())
                .body(assembler.toModel(nuevaPedido));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar un pedido", description = "Está API se encarga de actualizar todo un pedido")
        public ResponseEntity<EntityModel<Pedido>> putPedido(@PathVariable Long id, @RequestBody Pedido pedido){
        Pedido pedidoActualizada = pedidoService.actualizarPedido(id, pedido);
        if (pedidoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(pedidoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza un pedido parcialmente", description = "Está API se encarga de actualizar parcialmente un pedido")
        public ResponseEntity<EntityModel<Pedido>> patchPedido(@PathVariable Long id, @RequestBody Pedido pedido){
        Pedido pedidoActualizada = pedidoService.actualizarPedidoParcial(id, pedido);
        if (pedidoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(pedidoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina un pedido", description = "Está API se encarga de eliminar un pedido")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        Pedido pedidoExistente = pedidoService.obtenerPedidoPorId(id);
        if (pedidoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
}
