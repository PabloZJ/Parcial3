package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.ProductoCategoriaControllerV2;
import com.tech_nova.tech_nova.model.ProductoCategoria;

@Component
public class ProductoCategoriaModelAssembler implements RepresentationModelAssembler<ProductoCategoria, EntityModel<ProductoCategoria>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<ProductoCategoria> toModel(ProductoCategoria productoCategoria){
        return EntityModel.of(productoCategoria,
                linkTo(methodOn(ProductoCategoriaControllerV2.class).getAllProductoCategorias()).withRel("ProductoCategorias"),
                linkTo(methodOn(ProductoCategoriaControllerV2.class).getProductoCategoriaById(Long.valueOf(productoCategoria.getId()))).withRel("ProductoCategoria por id"),
                linkTo(methodOn(ProductoCategoriaControllerV2.class).createProductoCategoria(productoCategoria)).withRel("Crear productoCategoria"),
                linkTo(methodOn(ProductoCategoriaControllerV2.class).putProductoCategoria(Long.valueOf(productoCategoria.getId()),productoCategoria)).withRel("Actualizar productoCategoria"),
                linkTo(methodOn(ProductoCategoriaControllerV2.class).patchProductoCategoria(Long.valueOf(productoCategoria.getId()),productoCategoria)).withRel("Actualizar productoCategoria parcialmente"),
                linkTo(methodOn(ProductoCategoriaControllerV2.class).deleteProductoCategoria(Long.valueOf(productoCategoria.getId()))).withRel("Eliminar productoCategoria")
                );
    }
}
