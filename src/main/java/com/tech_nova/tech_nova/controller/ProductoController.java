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

import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.obtenerProductos();
        if(productos.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if(producto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(producto);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Producto> buscarProductoPorNombre(@PathVariable String nombre) {
        Producto producto = productoService.obtenerProductoNombre(nombre);
        if(producto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(producto);
    }
    @GetMapping("/precio/{precio}")
    public ResponseEntity<List<Producto>> buscarProductoPorPrecios(@PathVariable Integer precio) {
        List <Producto> producto = productoService.obtenerProductoPorPrecios(precio);
        if(producto.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(producto);
    }
    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return ResponseEntity.status(200).body(nuevoProducto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto){
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        if (productoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoActualizado);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> editar(@PathVariable Long id, @RequestBody Producto producto){
        Producto productoActualizado = productoService.actualizarProductoParcial(id, producto);
        if (productoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
