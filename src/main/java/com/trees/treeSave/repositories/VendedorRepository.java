package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Vendedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, String> {

    @Query("select v from Vendedor v where"
            + " v.cuit LIKE :query or"
            + " v.nombre LIKE :query or"
            + " v.domicilio LIKE :query or"
            + " v.ciudad.nombre LIKE :query or"
            + " v.contactoMail LIKE :query or"
            + " v.contactoCel LIKE :query")
    List<Vendedor> findAllByQ(@Param("query") String query);
    
    

    @Query("select v from Vendedor v where v.ciudad.nombre = :query")
    List<Vendedor> findAllByCiudad(@Param("query") String query);

    @Query("select v from Vendedor v where v.cuit = :cuit")
    public Vendedor findByCuit(@Param("cuit") String cuit);
}
