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

import com.tech_nova.tech_nova.assemblers.EnvioModelAssembler;
import com.tech_nova.tech_nova.model.Envio;
import com.tech_nova.tech_nova.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/envios")
@Tag(name = "API que administra los envios")
public class EnvioControllerV2 {
    @Autowired
    private EnvioService envioService;

    @Autowired
    EnvioModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todos los envios", description = "Está API se encarga de llamar todos los envios existentes en la base de datos")
    public CollectionModel<EntityModel<Envio>> getAllEnvios(){
        List<EntityModel<Envio>> envios = envioService.obtenerEnvios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(envios,
                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withRel("Envios"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene un envio por id ", description = "Está API se encarga de llamar un envio por su id especifico")
    public ResponseEntity<EntityModel<Envio>> getEnvioById(@PathVariable Long id){
        Envio envio = envioService.obtenerEnvioPorId(id);
        if (envio == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(envio));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea un envio", description = "está API se encarga de crear un envio")
    public ResponseEntity<EntityModel<Envio>> createEnvio(@RequestBody Envio envio){
        Envio nuevaEnvio = envioService.guardarEnvio(envio);
        return ResponseEntity
                .created(linkTo(methodOn(EnvioControllerV2.class).getEnvioById(Long.valueOf(nuevaEnvio.getId()))).toUri())
                .body(assembler.toModel(nuevaEnvio));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar un envio", description = "Está API se encarga de actualizar todo un envio")
        public ResponseEntity<EntityModel<Envio>> putEnvio(@PathVariable Long id, @RequestBody Envio envio){
        Envio envioActualizada = envioService.actualizarEnvio(id, envio);
        if (envioActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(envioActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza un envio parcialmente", description = "Está API se encarga de actualizar parcialmente un envio")
        public ResponseEntity<EntityModel<Envio>> patchEnvio(@PathVariable Long id, @RequestBody Envio envio){
        Envio envioActualizada = envioService.actualizarEnvioParcial(id, envio);
        if (envioActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(envioActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina un envio", description = "Está API se encarga de eliminar un envio")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        Envio envioExistente = envioService.obtenerEnvioPorId(id);
        if (envioExistente == null) {
            return ResponseEntity.notFound().build();
        }
        envioService.eliminarEnvio(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
}
