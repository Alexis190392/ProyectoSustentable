package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Vendedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, String> {

    @Query("SELECT v FROM Vendedor v where"
            + "v.nombre LIKE :query or"
            + "v.cuit LIKE :query or"
            + "v.domicilio LIKE :query or"
            + "v.ciudad.nombre LIKE :query or"
            + "v.contactoMail LIKE :query or"
            + "v.contactoCel LIKE :query or")
    List<Vendedor> findId(@Param("query") String query);
    
    

    @Query("select v from Vendedor p where v.ciudad.nombre = :query")
    List<Vendedor> findAllByCiudad(@Param("query") String q);

    @Query("SELECT v FROM Vendedor v WHERE v.cuit = :cuit")
    public Vendedor findByCuit(@Param("cuit") String documento);
}
