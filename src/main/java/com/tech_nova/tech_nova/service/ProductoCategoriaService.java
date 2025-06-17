package com.tech_nova.tech_nova.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech_nova.tech_nova.model.ProductoCategoria;
import com.tech_nova.tech_nova.repository.ProductoCategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoCategoriaService {
    
    @Autowired
    private ProductoCategoriaRepository productoCategoriaRepository;

    //Obtener
    public List<ProductoCategoria> obtenerProductoCategorias(){
        return productoCategoriaRepository.findAll();
    }

    //Obtener id
    public ProductoCategoria obtenerProductoCategoriaPorId (Long id){
        return productoCategoriaRepository.findById(id).orElse(null);
    }

    //Guardar
    public ProductoCategoria guardarProductoCategoria (ProductoCategoria productoCategoria){
        return productoCategoriaRepository.save(productoCategoria);

    }
    //Eliminar
    public void eliminarProductoCategoria(Long id){
        productoCategoriaRepository.deleteById(id);
    }
    //ActualizarTodo
    public ProductoCategoria actualizarProductoCategoria(Long id, ProductoCategoria productoCategoria){
        ProductoCategoria productoCategoriaExistente = productoCategoriaRepository.findById(id).orElse(null);
        if (productoCategoriaExistente != null){
            productoCategoriaExistente.setNombre(productoCategoria.getNombre());
            productoCategoriaExistente.setDescripcion(productoCategoria.getDescripcion());
            return productoCategoriaRepository.save(productoCategoriaExistente);
        }
        else{
            return null;
        }
    }
    //ActualizarPath
    public ProductoCategoria actualizarProductoCategoriaParcial(Long id, ProductoCategoria ProductoCategoria){
    ProductoCategoria productoCategoriaExistente = productoCategoriaRepository.findById(id).orElse(null);
    if (productoCategoriaExistente != null){
        if(ProductoCategoria.getNombre() != null)
            productoCategoriaExistente.setNombre(ProductoCategoria.getNombre());
        if(ProductoCategoria.getDescripcion() != null)
            productoCategoriaExistente.setDescripcion(ProductoCategoria.getDescripcion());
        return productoCategoriaRepository.save(productoCategoriaExistente);
    }
    else{
        return null;
    }
    }
}

