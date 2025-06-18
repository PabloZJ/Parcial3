package com.tech_nova.tech_nova.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tech_nova.tech_nova.controller.UsuarioControllerV2;
import com.tech_nova.tech_nova.model.Usuario;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("Usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(Long.valueOf(usuario.getId()))).withRel("Usuario por id"),
                linkTo(methodOn(UsuarioControllerV2.class).createUsuario(usuario)).withRel("Crear usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).putUsuario(usuario.getId(),usuario)).withRel("Actualizar usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuario.getId(),usuario)).withRel("Actualizar usuario parcialmente"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(Long.valueOf(usuario.getId()))).withRel("Eliminar usuario")
                );
    }
}
