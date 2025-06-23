package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.EnvioControllerV2;
import com.tech_nova.tech_nova.model.Envio;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Envio> toModel(Envio envio){
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withRel("Envios"),
                linkTo(methodOn(EnvioControllerV2.class).getEnvioById(Long.valueOf(envio.getId()))).withRel("Envio por id"),
                linkTo(methodOn(EnvioControllerV2.class).createEnvio(envio)).withRel("Crear envio"),
                linkTo(methodOn(EnvioControllerV2.class).putEnvio(Long.valueOf(envio.getId()),envio)).withRel("Actualizar envio"),
                linkTo(methodOn(EnvioControllerV2.class).patchEnvio(Long.valueOf(envio.getId()),envio)).withRel("Actualizar envio parcialmente"),
                linkTo(methodOn(EnvioControllerV2.class).deleteEnvio(Long.valueOf(envio.getId()))).withRel("Eliminar envio")
                );
    }
}
