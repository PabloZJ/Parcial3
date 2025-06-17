package com.tech_nova.tech_nova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.model.ProductoCategoria;
import com.tech_nova.tech_nova.repository.ProductoCategoriaRepository;
import com.tech_nova.tech_nova.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoCategoriaRepository productoCategoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

//Obtener lista productos
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

//Obtener producto por id
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
//Obtener por nombre
    public Producto obtenerProductoNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

//Obtener por Precio
    public List<Producto> obtenerProductoPorPrecios(Integer precio) {
            return productoRepository.findByPrecio(precio);
    }

    public List<Producto> findByProductoCategoria(String nombre) {
        ProductoCategoria productoCategoria = productoCategoriaRepository.findByNombre(nombre);
        if (productoCategoria != null) {
            return productoRepository.findByProductoCategoria(productoCategoria);
        }
        return null;
    }
    public Producto buscarProductoCategoria(Producto producto){
    if (producto.getProductoCategoria() != null && producto.getProductoCategoria().getId() != null) {
        Long categoriaId = ((Number) producto.getProductoCategoria().getId()).longValue();
        ProductoCategoria productoCategoria = productoCategoriaRepository.findById(categoriaId).orElseThrow(null);
        producto.setProductoCategoria(productoCategoria);
        return producto;
    }
    return null;
    }
//Guardar producto 
public Producto guardarProducto(Producto producto) {
    Producto productoValidado = buscarProductoCategoria(producto); 
    if(productoValidado != null)
        return productoRepository.save(producto);
    else
        return null;
}

//Eliminar 

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
//ActualizarTodo

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);
        if (productoExistente != null) {
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setStock(producto.getStock());
            if (producto.getProductoCategoria() != null && producto.getProductoCategoria().getId() != null) {
            Long categoriaId = ((Number) producto.getProductoCategoria().getId()).longValue();
            ProductoCategoria categoria = productoCategoriaRepository.findById(categoriaId).orElseThrow();
            productoExistente.setProductoCategoria(categoria);
            }
            return productoRepository.save(productoExistente);
        } else {
            return null;
        }
    }
//ActualizarPath

    public Producto actualizarProductoParcial(Long id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);
        if (productoExistente != null) {
            if (producto.getNombre() != null) {
                productoExistente.setNombre(producto.getNombre());
            }
            if (producto.getDescripcion() != null) {
                productoExistente.setDescripcion(producto.getDescripcion());
            }
            if (producto.getPrecio() != 0) {
                productoExistente.setPrecio(producto.getPrecio());
            }
            if (producto.getStock() != 0) {
                productoExistente.setStock(producto.getStock());
            }
            if (producto.getProductoCategoria() != null && producto.getProductoCategoria().getId() != null) {
                Long categoriaId = ((Number) producto.getProductoCategoria().getId()).longValue();
                ProductoCategoria categoria = productoCategoriaRepository.findById(categoriaId).orElseThrow();
                productoExistente.setProductoCategoria(categoria);
            }
            return productoRepository.save(productoExistente);
        } else {
            return null;
        }
    }
}
