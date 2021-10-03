package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Cliente;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fede
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.contactoMail = :contactoMail")
    public Cliente buscarPorContactoMail(@Param("contactoMail") String contactoMail);

    @Query("SELECT c FROM Cliente c WHERE c.nombres = :nombres")
    public Cliente buscarPorNombres(@Param("nombres") String nombres);

    @Query("SELECT c FROM Cliente c WHERE c.documento like :documento")
    public Cliente buscarPorDocumento(@Param("documento") String documento);

    @Query("SELECT c FROM Cliente c WHERE c.documento LIKE :q or c.nombres LIKE :q"
            + " or c.contactoCel LIKE :q or c.apellido LIKE :q or c.nivel LIKE :q"
            + " or c.contactoMail LIKE :q")
    public List<Cliente> findAllByQ(@Param("q") String q);

}
