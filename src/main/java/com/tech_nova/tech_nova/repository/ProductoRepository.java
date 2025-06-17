package com.tech_nova.tech_nova.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech_nova.tech_nova.model.Producto;
import com.tech_nova.tech_nova.model.ProductoCategoria;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{


    Producto findById(long id);
    Producto findByNombre(String nombre);
    List<Producto> findByStock(int stock);
    List<Producto> findByProductoCategoria(ProductoCategoria idProductoCategoria);
    List<Producto> findByPrecio(int precio);
}
