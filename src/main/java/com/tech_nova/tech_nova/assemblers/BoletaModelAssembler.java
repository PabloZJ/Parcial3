package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.BoletaControllerV2;
import com.tech_nova.tech_nova.model.Boleta;

@Component
public class BoletaModelAssembler implements RepresentationModelAssembler<Boleta, EntityModel<Boleta>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Boleta> toModel(Boleta boleta){
        return EntityModel.of(boleta,
                linkTo(methodOn(BoletaControllerV2.class).getAllBoletas()).withRel("Boletas"),
                linkTo(methodOn(BoletaControllerV2.class).getBoletaById(Long.valueOf(boleta.getId()))).withRel("Boleta por id"),
                linkTo(methodOn(BoletaControllerV2.class).createBoleta(boleta)).withRel("Crear boleta"),
                linkTo(methodOn(BoletaControllerV2.class).putBoleta(boleta.getId(),boleta)).withRel("Actualizar boleta"),
                linkTo(methodOn(BoletaControllerV2.class).patchBoleta(boleta.getId(),boleta)).withRel("Actualizar boleta parcialmente"),
                linkTo(methodOn(BoletaControllerV2.class).deleteBoleta(Long.valueOf(boleta.getId()))).withRel("Eliminar boleta"),
                linkTo(methodOn(BoletaControllerV2.class).getBoletasPorFecha(null)).withRel("Buscar boletas por fecha especifica"),
                linkTo(methodOn(BoletaControllerV2.class).getBoletasPorTipoPago(boleta.getTipoPago().getId())).withRel("Buscar boletas por tipo de pago"),
                linkTo(methodOn(BoletaControllerV2.class).getBoletasPorUsuario(null)).withRel("Buscar boletas por usuario"),
                linkTo(methodOn(BoletaControllerV2.class).getBoletasPorFechaYUsuario(null,null)).withRel("Buscar boletas por fecha y usuario"),
                linkTo(methodOn(BoletaControllerV2.class).getBoletasPorTipoPagoYMonto(null,null)).withRel("Buscar boletas por tipo pago y monto"),
                linkTo(methodOn(BoletaControllerV2.class).getBoletasConPedidoEntregado()).withRel("Buscar boletas por tipo pago y monto")
                );
    }
}
