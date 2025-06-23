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

import com.tech_nova.tech_nova.assemblers.DetallePedidoModelAssembler;
import com.tech_nova.tech_nova.model.DetallePedido;
import com.tech_nova.tech_nova.service.DetallePedidoService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/detalle-pedidos")
@Tag(name = "API que administra las detallePedidoes")
public class DetallePedidoControllerV2 {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    DetallePedidoModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todas los detalle de pedidos", description = "Está API se encarga de llamar todas los detalle de pedidos existentes en la base de datos")
    public CollectionModel<EntityModel<DetallePedido>> getAllDetallePedidos(){
        List<EntityModel<DetallePedido>> detallePedidoes = detallePedidoService.obtenerDetallePedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(detallePedidoes,
                linkTo(methodOn(DetallePedidoControllerV2.class).getAllDetallePedidos()).withRel("DetallePedidoes"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene un detalle de pedido por id ", description = "Está API se encarga de llamar un detalle de pedido por su id especifico")
    public ResponseEntity<EntityModel<DetallePedido>> getDetallePedidoById(@PathVariable Long id){
        DetallePedido detallePedido = detallePedidoService.obtenerDetallePedidoPorId(id);
        if (detallePedido == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(detallePedido));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea un detalle de pedido", description = "está API se encarga de crear un detalle de pedido")
    public ResponseEntity<EntityModel<DetallePedido>> createDetallePedido(@RequestBody DetallePedido detallePedido){
        DetallePedido nuevaDetallePedido = detallePedidoService.guardarDetallePedido(detallePedido);
        return ResponseEntity
                .created(linkTo(methodOn(DetallePedidoControllerV2.class).getDetallePedidoById(Long.valueOf(nuevaDetallePedido.getId()))).toUri())
                .body(assembler.toModel(nuevaDetallePedido));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar un detalle de pedido", description = "Está API se encarga de actualizar toda un detalle de pedido")
    public ResponseEntity<EntityModel<DetallePedido>> putDetallePedido(@PathVariable Long id, @RequestBody DetallePedido detallePedido){
        DetallePedido detallePedidoActualizada = detallePedidoService.actualizarDetallePedido(id, detallePedido);
        if (detallePedidoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(detallePedidoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza una direccón parcialmente", description = "Está API se encarga de actualizar parcialmente un detalle de pedido")
        public ResponseEntity<EntityModel<DetallePedido>> patchDetallePedido(@PathVariable Long id, @RequestBody DetallePedido detallePedido){
        DetallePedido detallePedidoActualizada = detallePedidoService.actualizarDetallePedidoParcial(id, detallePedido);
        if (detallePedidoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(detallePedidoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina un detalle de pedido", description = "Está API se encarga de eliminar un detalle de pedido")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable Long id) {
        DetallePedido detallePedidoExistente = detallePedidoService.obtenerDetallePedidoPorId(id);
        if (detallePedidoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        detallePedidoService.eliminarDetallePedido(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------
}
