package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fede
 */
@Repository
public interface FotoRepository extends JpaRepository<Foto,String>{

}
