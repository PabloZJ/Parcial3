package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.TipoPagoControllerV2;
import com.tech_nova.tech_nova.model.TipoPago;

@Component
public class TipoPagoModelAssembler implements RepresentationModelAssembler<TipoPago, EntityModel<TipoPago>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoPago> toModel(TipoPago tipoPago){
        return EntityModel.of(tipoPago,
                linkTo(methodOn(TipoPagoControllerV2.class).getAllTipoPagos()).withRel("TipoPagos"),
                linkTo(methodOn(TipoPagoControllerV2.class).getTipoPagoById(Long.valueOf(tipoPago.getId()))).withRel("TipoPago por id"),
                linkTo(methodOn(TipoPagoControllerV2.class).createTipoPago(tipoPago)).withRel("Crear tipoPago"),
                linkTo(methodOn(TipoPagoControllerV2.class).putTipoPago(Long.valueOf(tipoPago.getId()),tipoPago)).withRel("Actualizar tipoPago"),
                linkTo(methodOn(TipoPagoControllerV2.class).patchTipoPago(Long.valueOf(tipoPago.getId()),tipoPago)).withRel("Actualizar tipoPago parcialmente"),
                linkTo(methodOn(TipoPagoControllerV2.class).deleteTipoPago(Long.valueOf(tipoPago.getId()))).withRel("Eliminar tipoPago")
                );
    }
}
