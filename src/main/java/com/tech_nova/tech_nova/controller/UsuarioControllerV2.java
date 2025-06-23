package com.tech_nova.tech_nova.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech_nova.tech_nova.assemblers.UsuarioModelAssembler;
import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "API que administra los usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    UsuarioModelAssembler assembler;
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API llama a todos los usuarios", description = "Está API se encarga de llamar todas los usuarios existentes en la base de datos")
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios(){
        List<EntityModel<Usuario>> usuarios = usuarioService.obtenerUsuarios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("Usuarios"));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API obtiene un usuario por id ", description = "Está API se encarga de llamar un usuario por su id especifico")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id){
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API crea un usuario", description = "está API se encarga de crear un usuario")
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario){
        Usuario nuevaUsuario = usuarioService.guardarUsuario(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(Long.valueOf(nuevaUsuario.getId()))).toUri())
                .body(assembler.toModel(nuevaUsuario));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualizar un usuario", description = "Está API se encarga de actualizar todo un usuario")
        public ResponseEntity<EntityModel<Usuario>> putUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario usuarioActualizada = usuarioService.actualizarUsuario(id, usuario);
        if (usuarioActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuarioActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API actualiza un usuario parcialmente", description = "Está API se encarga de actualizar parcialmente un usuario")
        public ResponseEntity<EntityModel<Usuario>> patchUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario usuarioActualizada = usuarioService.actualizarUsuarioParcial(id, usuario);
        if (usuarioActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuarioActualizada));
    }
//-------------------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Está API elimina un usuario", description = "Está API se encarga de eliminar un usuario")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
//-------------------------------------------------------------------------------------------------------------------------------

//-------------------------------------------------------------------------------------------------------------------------------
}
