package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.DetallePedidoControllerV2;
import com.tech_nova.tech_nova.model.DetallePedido;

@Component
public class DetallePedidoModelAssembler implements RepresentationModelAssembler<DetallePedido, EntityModel<DetallePedido>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<DetallePedido> toModel(DetallePedido detallePedido){
        return EntityModel.of(detallePedido,
                linkTo(methodOn(DetallePedidoControllerV2.class).getAllDetallePedidos()).withRel("DetallePedidos"),
                linkTo(methodOn(DetallePedidoControllerV2.class).getDetallePedidoById(Long.valueOf(detallePedido.getId()))).withRel("DetallePedido por id"),
                linkTo(methodOn(DetallePedidoControllerV2.class).createDetallePedido(detallePedido)).withRel("Crear detallePedido"),
                linkTo(methodOn(DetallePedidoControllerV2.class).putDetallePedido(Long.valueOf(detallePedido.getId()),detallePedido)).withRel("Actualizar detallePedido"),
                linkTo(methodOn(DetallePedidoControllerV2.class).patchDetallePedido(Long.valueOf(detallePedido.getId()),detallePedido)).withRel("Actualizar detallePedido parcialmente"),
                linkTo(methodOn(DetallePedidoControllerV2.class).deleteDetallePedido(Long.valueOf(detallePedido.getId()))).withRel("Eliminar detallePedido")
                );
    }
}
