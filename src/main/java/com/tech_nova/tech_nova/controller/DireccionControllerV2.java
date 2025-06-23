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

import com.tech_nova.tech_nova.assemblers.DireccionModelAssembler;
import com.tech_nova.tech_nova.model.Direccion;
import com.tech_nova.tech_nova.service.DireccionService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/direcciones")
@Tag(name = "API que administra las direcciones")
public class DireccionControllerV2 {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    DireccionModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todas las direcciones", description = "Está API se encarga de llamar todas las direcciones existentes en la base de datos")
    public CollectionModel<EntityModel<Direccion>> getAllDirecciones(){
        List<EntityModel<Direccion>> direcciones = direccionService.obtenerDirecciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(direcciones,
                linkTo(methodOn(DireccionControllerV2.class).getAllDirecciones()).withRel("Direcciones"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene una dirección por id ", description = "Está API se encarga de llamar una dirección por su id especifico")
    public ResponseEntity<EntityModel<Direccion>> getDireccionById(@PathVariable Long id){
        Direccion direccion = direccionService.obtenerDireccionPorId(id);
        if (direccion == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(direccion));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea una dirección", description = "está API se encarga de crear una dirección")
    public ResponseEntity<EntityModel<Direccion>> createDireccion(@RequestBody Direccion direccion){
        Direccion nuevaDireccion = direccionService.guardarDireccion(direccion);
        return ResponseEntity
                .created(linkTo(methodOn(DireccionControllerV2.class).getDireccionById(Long.valueOf(nuevaDireccion.getId()))).toUri())
                .body(assembler.toModel(nuevaDireccion));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar una dirección", description = "Está API se encarga de actualizar toda una dirección")
    public ResponseEntity<EntityModel<Direccion>> putDireccion(@PathVariable Long id, @RequestBody Direccion direccion){
        Direccion direccionActualizada = direccionService.actualizarDireccion(id, direccion);
        if (direccionActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(direccionActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza una direccón parcialmente", description = "Está API se encarga de actualizar parcialmente una dirección")
        public ResponseEntity<EntityModel<Direccion>> patchDireccion(@PathVariable Long id, @RequestBody Direccion direccion){
        Direccion direccionActualizada = direccionService.actualizarDireccionParcial(id, direccion);
        if (direccionActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(direccionActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina una dirección", description = "Está API se encarga de eliminar una dirección")
    public ResponseEntity<Void> deleteDireccion(@PathVariable Long id) {
        Direccion direccionExistente = direccionService.obtenerDireccionPorId(id);
        if (direccionExistente == null) {
            return ResponseEntity.notFound().build();
        }
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------
}
