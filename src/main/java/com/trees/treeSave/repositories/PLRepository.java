package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.Entity.ProductoLista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PLRepository extends JpaRepository<ProductoLista,String> {
    @Query("SELECT p FROM ProductoLista p where p.documento LIKE :documento")
    List<ProductoLista> listaAllPL(@Param("documento") String documento); 
    
    @Query("SELECT p from ProductoLista p where p.documento like :documento AND p.codigoBarra like :sku")
    ProductoLista forDelete(@Param("documento") String documento, @Param("sku") String sku);

}