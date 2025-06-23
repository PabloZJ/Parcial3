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

import com.tech_nova.tech_nova.assemblers.ProductoModelAssembler;
import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/productos")
@Tag(name = "API que administra los productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    ProductoModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todos los productos", description = "Está API se encarga de llamar todas los productos existentes en la base de datos")
    public CollectionModel<EntityModel<Producto>> getAllProductos(){
        List<EntityModel<Producto>> productos = productoService.obtenerProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("Productos"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene un producto por id ", description = "Está API se encarga de llamar un producto por su id especifico")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id){
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea un producto", description = "está API se encarga de crear un producto")
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto){
        Producto nuevaProducto = productoService.guardarProducto(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(Long.valueOf(nuevaProducto.getId()))).toUri())
                .body(assembler.toModel(nuevaProducto));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar un producto", description = "Está API se encarga de actualizar todo un producto")
        public ResponseEntity<EntityModel<Producto>> putProducto(@PathVariable Long id, @RequestBody Producto producto){
        Producto productoActualizada = productoService.actualizarProducto(id, producto);
        if (productoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza un producto parcialmente", description = "Está API se encarga de actualizar parcialmente un producto")
        public ResponseEntity<EntityModel<Producto>> patchProducto(@PathVariable Long id, @RequestBody Producto producto){
        Producto productoActualizada = productoService.actualizarProductoParcial(id, producto);
        if (productoActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productoActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina un producto", description = "Está API se encarga de eliminar un producto")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto productoExistente = productoService.obtenerProductoPorId(id);
        if (productoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
}
//-------------------------------------------------------------------------------------------------------------------------------