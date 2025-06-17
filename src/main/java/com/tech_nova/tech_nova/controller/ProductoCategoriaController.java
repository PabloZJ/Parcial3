package com.tech_nova.tech_nova.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech_nova.tech_nova.model.ProductoCategoria;
import com.tech_nova.tech_nova.service.ProductoCategoriaService;

@RestController
@RequestMapping("/api/v1/producto-categoria")
public class ProductoCategoriaController {
    
    @Autowired
    private ProductoCategoriaService productoCategoriaService;

    @GetMapping
    public ResponseEntity<List<ProductoCategoria>> listar() {
        List<ProductoCategoria> productoCategorias = productoCategoriaService.obtenerProductoCategorias();
        if(productoCategorias.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productoCategorias);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoCategoria> buscarproductoCategoriaPorId(@PathVariable Long id) {
        ProductoCategoria productoCategoria = productoCategoriaService.obtenerProductoCategoriaPorId(id);
        if(productoCategoria == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productoCategoria);
    }
    @PostMapping
    public ResponseEntity<ProductoCategoria> guardar(@RequestBody ProductoCategoria productoCategoria){
        ProductoCategoria nuevoProductoCategoria = productoCategoriaService.guardarProductoCategoria(productoCategoria);
        return ResponseEntity.status(200).body(nuevoProductoCategoria);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductoCategoria> actualizar(@PathVariable Long id, @RequestBody ProductoCategoria productoCategoria){
        ProductoCategoria productoCategoriaActualizado = productoCategoriaService.actualizarProductoCategoria(id, productoCategoria);
        if (productoCategoriaActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoCategoriaActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ProductoCategoria> editar(@PathVariable Long id, @RequestBody ProductoCategoria productoCategoria){
        ProductoCategoria productoCategoriaActualizado = productoCategoriaService.actualizarProductoCategoriaParcial(id, productoCategoria);
        if (productoCategoriaActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoCategoriaActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        productoCategoriaService.eliminarProductoCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
