package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.tech_nova.tech_nova.controller.DireccionControllerV2;
import com.tech_nova.tech_nova.model.Direccion;

@Component
public class DireccionModelAssembler implements RepresentationModelAssembler<Direccion, EntityModel<Direccion>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Direccion> toModel(Direccion direccion){
        return EntityModel.of(direccion,
                linkTo(methodOn(DireccionControllerV2.class).getAllDirecciones()).withRel("Direcciones"),
                linkTo(methodOn(DireccionControllerV2.class).getDireccionById(Long.valueOf(direccion.getId()))).withRel("Direccion por id"),
                linkTo(methodOn(DireccionControllerV2.class).createDireccion(direccion)).withRel("Crear direccion"),
                linkTo(methodOn(DireccionControllerV2.class).putDireccion(Long.valueOf(direccion.getId()),direccion)).withRel("Actualizar direccion"),
                linkTo(methodOn(DireccionControllerV2.class).patchDireccion(Long.valueOf(direccion.getId()),direccion)).withRel("Actualizar direccion parcialmente"),
                linkTo(methodOn(DireccionControllerV2.class).deleteDireccion(Long.valueOf(direccion.getId()))).withRel("Eliminar direccion")
                );
    }
}