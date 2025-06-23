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

import com.tech_nova.tech_nova.assemblers.EstadoPedidoModelAssembler;
import com.tech_nova.tech_nova.model.EstadoPedido;
import com.tech_nova.tech_nova.service.EstadoPedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/estado-pedidos")
@Tag(name = "API que administra los estadoPedidos")
public class EstadoPedidoControllerV2 {
    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @Autowired
    EstadoPedidoModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todos los estados de pedido", description = "Está API se encarga de llamar todos los estados de pedido existentes en la base de datos")
    public CollectionModel<EntityModel<EstadoPedido>> getAllEstadoPedidos(){
        List<EntityModel<EstadoPedido>> estadoPedidos = estadoPedidoService.obtenerEstadoPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(estadoPedidos,
                linkTo(methodOn(EstadoPedidoControllerV2.class).getAllEstadoPedidos()).withRel("EstadoPedidos"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene un estado de pedido por id ", description = "Está API se encarga de llamar un estado de pedido por su id especifico")
    public ResponseEntity<EntityModel<EstadoPedido>> getEstadoPedidoById(@PathVariable Long id){
        EstadoPedido estadoPedido = estadoPedidoService.obtenerEstadoPedidoPorId(id);
        if (estadoPedido == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(estadoPedido));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea un estado de pedido", description = "está API se encarga de crear un estado de pedido")
    public ResponseEntity<EntityModel<EstadoPedido>> createEstadoPedido(@RequestBody EstadoPedido estadoPedido){
        EstadoPedido nuevaEstadoPedido = estadoPedidoService.guardarEstadoPedido(estadoPedido);
        return ResponseEntity
                .created(linkTo(methodOn(EstadoPedidoControllerV2.class).getEstadoPedidoById(Long.valueOf(nuevaEstadoPedido.getId()))).toUri())
                .body(assembler.toModel(nuevaEstadoPedido));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar un estado de pedido", description = "Está API se encarga de actualizar todo un estado de pedido")
        public ResponseEntity<EntityModel<EstadoPedido>> putEstadoPedido(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido){
        EstadoPedido estadoPedidoActualizada = estadoPedidoService.actualizarEstadoPedido(id, estadoPedido);
        if (estadoPedidoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(estadoPedidoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza un estado de pedido parcialmente", description = "Está API se encarga de actualizar parcialmente un estado de pedido")
        public ResponseEntity<EntityModel<EstadoPedido>> patchEstadoPedido(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido){
        EstadoPedido estadoPedidoActualizada = estadoPedidoService.actualizarEstadoPedidoParcial(id, estadoPedido);
        if (estadoPedidoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(estadoPedidoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina un estado de pedido", description = "Está API se encarga de eliminar un estado de pedido")
    public ResponseEntity<Void> deleteEstadoPedido(@PathVariable Long id) {
        EstadoPedido estadoPedidoExistente = estadoPedidoService.obtenerEstadoPedidoPorId(id);
        if (estadoPedidoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        estadoPedidoService.eliminarEstadoPedido(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
}
