package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Vendedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor,String> {
    @Query("SELECT v FROM Vendedor v where "
            + "v.nombre LIKE :query or "
            + "v.cuit LIKE :query or"
            + "v.domicilio LIKE :query")
    List<Vendedor> searchId(@Param("query") String query);
}
