package com.tech_nova.tech_nova.service;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tech_nova.tech_nova.model.Usuario;
import com.tech_nova.tech_nova.repository.UsuarioRepository;

@SpringBootTest
public class UsuarioServiceTest {
    
    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    UsuarioRepository usuarioRepository;
//------------------------------------------------------
    private Usuario createUsuario(){
        return new Usuario(
            1,
            "Papi Micky",
            "papimicky@papimicky.com",
            "papimicky123",
            new Date(),
            "56966666666"
            );
    }
//------------------------------------------------------
    @Test
    public void testFindAll(){
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioRepository.findAll();
        assertNotNull(usuarios);
        assertEquals(1,usuarios.size());
    }
//------------------------------------------------------
    @Test
    public void testFindById(){
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioService.obtenerUsuarioPorId(1L);
        assertNotNull(usuario);
        assertEquals("Papi Micky", usuario.getNombreCompleto());
    }
//------------------------------------------------------
    @Test
    public void testSave(){
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
        assertNotNull(usuarioGuardado);
        assertEquals(1, usuarioGuardado.getId());
    }
//------------------------------------------------------
    @Test
    public void testDeleteById(){
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.eliminarUsuario(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
//------------------------------------------------------
    @Test
    public void testPut(){
        Usuario usuarioExistente = createUsuario();
        Usuario pathData = new Usuario();
        pathData.setNombreCompleto("Pasta Micky");

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        Usuario usuarioActualizado = usuarioService.actualizarUsuario(1L, pathData);
        assertNotNull(usuarioActualizado);
        assertEquals("Pasta Micky", usuarioActualizado.getNombreCompleto());
    }
//------------------------------------------------------
    @Test
    public void testPath(){
        Usuario usuarioExistente = createUsuario();
        Usuario pathData = new Usuario();
        pathData.setNombreCompleto("Pasta Micky");

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        Usuario usuarioActualizado = usuarioService.actualizarUsuarioParcial(1L, pathData);
        assertNotNull(usuarioActualizado);
        assertEquals("Pasta Micky", usuarioActualizado.getNombreCompleto());
    }
//------------------------------------------------------
}
