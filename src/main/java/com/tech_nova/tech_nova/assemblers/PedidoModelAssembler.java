package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.PedidoControllerV2;
import com.tech_nova.tech_nova.model.Pedido;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Pedido> toModel(Pedido pedido){
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withRel("Pedidos"),
                linkTo(methodOn(PedidoControllerV2.class).getPedidoById(Long.valueOf(pedido.getId()))).withRel("Pedido por id"),
                linkTo(methodOn(PedidoControllerV2.class).createPedido(pedido)).withRel("Crear pedido"),
                linkTo(methodOn(PedidoControllerV2.class).putPedido(Long.valueOf(pedido.getId()),pedido)).withRel("Actualizar pedido"),
                linkTo(methodOn(PedidoControllerV2.class).patchPedido(Long.valueOf(pedido.getId()),pedido)).withRel("Actualizar pedido parcialmente"),
                linkTo(methodOn(PedidoControllerV2.class).deletePedido(Long.valueOf(pedido.getId()))).withRel("Eliminar pedido")
                );
    }
}
