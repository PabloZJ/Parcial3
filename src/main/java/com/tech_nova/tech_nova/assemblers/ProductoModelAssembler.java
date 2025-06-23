package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.ProductoControllerV2;
import com.tech_nova.tech_nova.model.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Producto> toModel(Producto producto){
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("Productos"),
                linkTo(methodOn(ProductoControllerV2.class).getProductoById(Long.valueOf(producto.getId()))).withRel("Producto por id"),
                linkTo(methodOn(ProductoControllerV2.class).createProducto(producto)).withRel("Crear producto"),
                linkTo(methodOn(ProductoControllerV2.class).putProducto(Long.valueOf(producto.getId()),producto)).withRel("Actualizar producto"),
                linkTo(methodOn(ProductoControllerV2.class).patchProducto(Long.valueOf(producto.getId()),producto)).withRel("Actualizar producto parcialmente"),
                linkTo(methodOn(ProductoControllerV2.class).deleteProducto(Long.valueOf(producto.getId()))).withRel("Eliminar producto")
                );
    }
}
