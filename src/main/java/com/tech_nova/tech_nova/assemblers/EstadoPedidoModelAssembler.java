package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.EstadoPedidoControllerV2;
import com.tech_nova.tech_nova.model.EstadoPedido;

@Component
public class EstadoPedidoModelAssembler implements RepresentationModelAssembler<EstadoPedido, EntityModel<EstadoPedido>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<EstadoPedido> toModel(EstadoPedido estadoPedido){
        return EntityModel.of(estadoPedido,
                linkTo(methodOn(EstadoPedidoControllerV2.class).getAllEstadoPedidos()).withRel("EstadoPedidos"),
                linkTo(methodOn(EstadoPedidoControllerV2.class).getEstadoPedidoById(Long.valueOf(estadoPedido.getId()))).withRel("EstadoPedido por id"),
                linkTo(methodOn(EstadoPedidoControllerV2.class).createEstadoPedido(estadoPedido)).withRel("Crear estadoPedido"),
                linkTo(methodOn(EstadoPedidoControllerV2.class).putEstadoPedido(Long.valueOf(estadoPedido.getId()),estadoPedido)).withRel("Actualizar estadoPedido"),
                linkTo(methodOn(EstadoPedidoControllerV2.class).patchEstadoPedido(Long.valueOf(estadoPedido.getId()),estadoPedido)).withRel("Actualizar estadoPedido parcialmente"),
                linkTo(methodOn(EstadoPedidoControllerV2.class).deleteEstadoPedido(Long.valueOf(estadoPedido.getId()))).withRel("Eliminar estadoPedido")
                );
    }
}
