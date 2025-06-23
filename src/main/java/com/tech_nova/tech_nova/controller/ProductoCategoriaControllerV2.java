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

import com.tech_nova.tech_nova.assemblers.ProductoCategoriaModelAssembler;
import com.tech_nova.tech_nova.model.ProductoCategoria;
import com.tech_nova.tech_nova.service.ProductoCategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/producto-categorias")
@Tag(name = "API que administra los producto de categorias")
public class ProductoCategoriaControllerV2 {

    @Autowired
    private ProductoCategoriaService productoCategoriaService;

    @Autowired
    ProductoCategoriaModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todos las categorias de productos", description = "Está API se encarga de llamar todas las categorias de productos existentes en la base de datos")
    public CollectionModel<EntityModel<ProductoCategoria>> getAllProductoCategorias(){
        List<EntityModel<ProductoCategoria>> productoCategorias = productoCategoriaService.obtenerProductoCategorias().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productoCategorias,
                linkTo(methodOn(ProductoCategoriaControllerV2.class).getAllProductoCategorias()).withRel("ProductoCategorias"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene una categoria de producto por id ", description = "Está API se encarga de llamar una categoria de producto por su id especifico")
    public ResponseEntity<EntityModel<ProductoCategoria>> getProductoCategoriaById(@PathVariable Long id){
        ProductoCategoria productoCategoria = productoCategoriaService.obtenerProductoCategoriaPorId(id);
        if (productoCategoria == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productoCategoria));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea una categoria de producto", description = "está API se encarga de crear una categoria de producto")
    public ResponseEntity<EntityModel<ProductoCategoria>> createProductoCategoria(@RequestBody ProductoCategoria productoCategoria){
        ProductoCategoria nuevaProductoCategoria = productoCategoriaService.guardarProductoCategoria(productoCategoria);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoCategoriaControllerV2.class).getProductoCategoriaById(Long.valueOf(nuevaProductoCategoria.getId()))).toUri())
                .body(assembler.toModel(nuevaProductoCategoria));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar una categoria de producto", description = "Está API se encarga de actualizar todo una categoria de producto")
        public ResponseEntity<EntityModel<ProductoCategoria>> putProductoCategoria(@PathVariable Long id, @RequestBody ProductoCategoria productoCategoria){
        ProductoCategoria productoCategoriaActualizada = productoCategoriaService.actualizarProductoCategoria(id, productoCategoria);
        if (productoCategoriaActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productoCategoriaActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza una categoria de producto", description = "Está API se encarga de actualizar parcialmente una categoria de producto")
        public ResponseEntity<EntityModel<ProductoCategoria>> patchProductoCategoria(@PathVariable Long id, @RequestBody ProductoCategoria productoCategoria){
        ProductoCategoria productoCategoriaActualizada = productoCategoriaService.actualizarProductoCategoriaParcial(id, productoCategoria);
        if (productoCategoriaActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productoCategoriaActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina una categoria de producto", description = "Está API se encarga de eliminar una categoria de producto")
    public ResponseEntity<Void> deleteProductoCategoria(@PathVariable Long id) {
        ProductoCategoria productoCategoriaExistente = productoCategoriaService.obtenerProductoCategoriaPorId(id);
        if (productoCategoriaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        productoCategoriaService.eliminarProductoCategoria(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------
}
//-------------------------------------------------------------------------------------------------------------------------------