
package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Ciudad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CiudadRepository extends JpaRepository <Ciudad, String> {
    @Query("select c from Ciudad c where c.nombre LIKE :q")
    List<Ciudad> findAll(@Param("q")String q);
    
    @Query("SELECT c FROM Ciudad c WHERE c.id like :idCiudad")
    public Ciudad buscarPorId(@Param("idCiudad") String idCiudad);
}
