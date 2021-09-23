package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fede
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {

    
    @Query("SELECT a FROM Categoria a WHERE a.nombreCat = :nombreCat")
    public Categoria buscarPorNombre(@Param("nombreCat") String nombreCat);
    
     @Query("SELECT a FROM Categoria a WHERE a.nombreCat LIKE :q")
    public List<Categoria> findAllByQ(@Param("q") String q);
}
