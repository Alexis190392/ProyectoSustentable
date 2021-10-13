package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.Entity.ProductoLista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,String> {
    @Query("SELECT p FROM Producto p where "
            + "p.codigoBarra LIKE :query or "
            + "p.nombre LIKE :query or "
            + "p.origen LIKE :query or "
            + "p.tipo LIKE :query or "
            + "p.categoria LIKE :query")
    List<Producto> searchId(@Param("query") String query);    
}