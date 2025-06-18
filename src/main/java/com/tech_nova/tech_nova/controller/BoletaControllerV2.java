package com.tech_nova.tech_nova.controller;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.tech_nova.tech_nova.assemblers.BoletaModelAssembler;
import com.tech_nova.tech_nova.model.Boleta;
import com.tech_nova.tech_nova.service.BoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/boletas")
@Tag(name = "API que administra las boletas")
public class BoletaControllerV2 {
    @Autowired
    private BoletaService boletaService;

    @Autowired
    BoletaModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todas las boletaes", description = "Está API se encarga de llamar todas las boletaes existentes en la base de datos")
    public CollectionModel<EntityModel<Boleta>> getAllBoletas(){
        List<EntityModel<Boleta>> boletas = boletaService.obtenerBoletas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(boletas,
                linkTo(methodOn(BoletaControllerV2.class).getAllBoletas()).withRel("Boletas"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene una boleta por id ", description = "Está API se encarga de llamar una boleta por su id especifico")
    public ResponseEntity<EntityModel<Boleta>> getBoletaById(@PathVariable long id){
        Boleta boleta = boletaService.obtenerBoletaPorId(id);
        if (boleta == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(boleta));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea una boleta", description = "está API se encarga de crear una boleta")
    public ResponseEntity<EntityModel<Boleta>> createBoleta(@RequestBody Boleta boleta){
        Boleta nuevaBoleta = boletaService.guardarBoleta(boleta);
        return ResponseEntity
                .created(linkTo(methodOn(BoletaControllerV2.class).getBoletaById(Long.valueOf(nuevaBoleta.getId()))).toUri())
                .body(assembler.toModel(nuevaBoleta));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar una boleta", description = "Está API se encarga de actualizar toda una boleta")
        public ResponseEntity<EntityModel<Boleta>> putBoleta(@PathVariable long id, @RequestBody Boleta boleta){
        Boleta boletaActualizada = boletaService.actualizarBoleta(id, boleta);
        if (boletaActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(boletaActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza una direccón parcialmente", description = "Está API se encarga de actualizar parcialmente una boleta")
        public ResponseEntity<EntityModel<Boleta>> patchBoleta(@PathVariable long id, @RequestBody Boleta boleta){
        Boleta boletaActualizada = boletaService.actualizarBoletaParcial(id, boleta);
        if (boletaActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(boletaActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina una boleta", description = "Está API se encarga de eliminar una boleta")
    public ResponseEntity<Void> deleteBoleta(@PathVariable Long id) {
        Boleta boletaExistente = boletaService.obtenerBoletaPorId(id);
        if (boletaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        boletaService.eliminarBoleta(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene boletas por fecha", description = "Está API se encarga filtrar las boletas por una fecha en especifica")
    public ResponseEntity<CollectionModel<EntityModel<Boleta>>> getBoletasPorFecha(@PathVariable String fecha) {
        Date fechaConvertida;
        try {
            fechaConvertida = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        //
        List<EntityModel<Boleta>> boletas = boletaService.getBoletasPorFecha(fechaConvertida)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        //
        return ResponseEntity.ok(
            CollectionModel.of(boletas, 
                linkTo(methodOn(BoletaControllerV2.class).getBoletasPorFecha(fecha)).withSelfRel())
        );
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/tipo-pago/{idTipoPago}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene boletas por el tipo de pago", description = "Está API se encarga de filtrar las boletas por el tipo de pago")
    public ResponseEntity<CollectionModel<EntityModel<Boleta>>> getBoletasPorTipoPago(@PathVariable Integer idTipoPago) {
        List<EntityModel<Boleta>> boletas = boletaService.getBoletasPorTipoPago(idTipoPago)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(boletas,
                linkTo(methodOn(BoletaControllerV2.class).getBoletasPorTipoPago(idTipoPago)).withSelfRel())
        );
        }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/usuario/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene boletas por el usuario", description = "Está API se encarga de filtrar las boletas por un usuario en especifico")
    public CollectionModel<EntityModel<Boleta>> getBoletasPorUsuario(@PathVariable Integer idUsuario) {
    List<EntityModel<Boleta>> boletas = boletaService.getBoletasPorUsuario(idUsuario).stream()
        .map(assembler::toModel).collect(Collectors.toList());

    return CollectionModel.of(boletas,
        linkTo(methodOn(BoletaControllerV2.class).getBoletasPorUsuario(idUsuario)).withSelfRel());
}
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/fecha/{fecha}/usuario/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene boletas por fecha y usuario", description = "Está API se encarga de filtrar boletas por fecha y usuario en especifico")
    public ResponseEntity<CollectionModel<EntityModel<Boleta>>> getBoletasPorFechaYUsuario(@PathVariable String fecha, @PathVariable Integer idUsuario) {
        Date fechaConvertida;
        try {
            fechaConvertida = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        List<EntityModel<Boleta>> boletas = boletaService.getBoletasPorFechaYUsuario(fechaConvertida, idUsuario).stream()
            .map(assembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(boletas,
            linkTo(methodOn(BoletaControllerV2.class).getBoletasPorFechaYUsuario(fecha, idUsuario)).withSelfRel()));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/tipopago/{idTipoPago}/monto/{monto}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene boletas por tipo de pago y un monto mayor", description = "Está API se encarga filtrar boletas por tipo de pago y un monto mayor especificado")
    public CollectionModel<EntityModel<Boleta>> getBoletasPorTipoPagoYMonto(
        @PathVariable Integer idTipoPago, @PathVariable Integer monto) {
        
        List<EntityModel<Boleta>> boletas = boletaService.getBoletasPorTipoPagoYMontoMayor(idTipoPago, monto).stream()
            .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(boletas,
            linkTo(methodOn(BoletaControllerV2.class).getBoletasPorTipoPagoYMonto(idTipoPago, monto)).withSelfRel());
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/estado/entregado", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene boletas por pedido entregado", description = "Está API se encarga filtrar boletas por pedidos que han sido entregados")
    public CollectionModel<EntityModel<Boleta>> getBoletasConPedidoEntregado() {
        List<EntityModel<Boleta>> boletas = boletaService.getBoletasConPedidoEntregado().stream()
            .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(boletas,
            linkTo(methodOn(BoletaControllerV2.class).getBoletasConPedidoEntregado()).withSelfRel());
    }
//-------------------------------------------------------------------------------------------------------------------------------
}
