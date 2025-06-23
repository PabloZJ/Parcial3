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

import com.tech_nova.tech_nova.assemblers.TipoPagoModelAssembler;
import com.tech_nova.tech_nova.model.TipoPago;
import com.tech_nova.tech_nova.service.TipoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/tipo-pagos")
@Tag(name = "API que administra los tipo de pagos")
public class TipoPagoControllerV2 {

    @Autowired
    private TipoPagoService tipoPagoService;

    @Autowired
    TipoPagoModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todos los tipo de pagos", description = "Está API se encarga de llamar todas los tipo de pago existentes en la base de datos")
    public CollectionModel<EntityModel<TipoPago>> getAllTipoPagos(){
        List<EntityModel<TipoPago>> tipoPagos = tipoPagoService.obtenerTipoPagos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tipoPagos,
                linkTo(methodOn(TipoPagoControllerV2.class).getAllTipoPagos()).withRel("TipoPagos"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene un tipo de pago por id ", description = "Está API se encarga de llamar un tipo de pago por su id especifico")
    public ResponseEntity<EntityModel<TipoPago>> getTipoPagoById(@PathVariable Long id){
        TipoPago tipoPago = tipoPagoService.obtenerTipoPagoPorId(id);
        if (tipoPago == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoPago));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea un tipo de pago", description = "está API se encarga de crear un tipo de pago")
    public ResponseEntity<EntityModel<TipoPago>> createTipoPago(@RequestBody TipoPago tipoPago){
        TipoPago nuevaTipoPago = tipoPagoService.guardarTipoPago(tipoPago);
        return ResponseEntity
                .created(linkTo(methodOn(TipoPagoControllerV2.class).getTipoPagoById(Long.valueOf(nuevaTipoPago.getId()))).toUri())
                .body(assembler.toModel(nuevaTipoPago));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar un tipo de pago", description = "Está API se encarga de actualizar todo un tipo de pago")
        public ResponseEntity<EntityModel<TipoPago>> putTipoPago(@PathVariable Long id, @RequestBody TipoPago tipoPago){
        TipoPago tipoPagoActualizada = tipoPagoService.actualizarTipoPago(id, tipoPago);
        if (tipoPagoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoPagoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza un tipo de pago parcialmente", description = "Está API se encarga de actualizar parcialmente un tipo de pago")
        public ResponseEntity<EntityModel<TipoPago>> patchTipoPago(@PathVariable Long id, @RequestBody TipoPago tipoPago){
        TipoPago tipoPagoActualizada = tipoPagoService.actualizarTipoPagoParcial(id, tipoPago);
        if (tipoPagoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoPagoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina un tipo de pago", description = "Está API se encarga de eliminar un tipo de pago")
    public ResponseEntity<Void> deleteTipoPago(@PathVariable Long id) {
        TipoPago tipoPagoExistente = tipoPagoService.obtenerTipoPagoPorId(id);
        if (tipoPagoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        tipoPagoService.eliminarTipoPago(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
}
//-------------------------------------------------------------------------------------------------------------------------------