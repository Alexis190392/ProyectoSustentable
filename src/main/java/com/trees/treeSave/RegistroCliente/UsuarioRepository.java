package com.trees.treeSave.RegistroCliente;

import com.trees.treeSave.Entity.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    
    @Query("SELECT u FROM Usuario u WHERE u.contactoMail = :contactoMail")
    public Usuario findByMail(@Param("contactoMail") String contactoMail);
    
    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    public Usuario findByUser(@Param("username") String username);

    @Query("SELECT u FROM Usuario u WHERE u.nombres = :nombres")
    public Usuario findByName(@Param("nombres") String nombres);

    @Query("SELECT u FROM Usuario u WHERE u.documento like :documento")
    public Usuario findbyDoc(@Param("documento") String documento);

//    @Query("SELECT c FROM Usuario c WHERE c.documento LIKE :q or c.nombres LIKE :q"
//            + " or c.contactoCel LIKE :q or c.apellido LIKE :q or c.nivel LIKE :q"
//            + " or c.contactoMail LIKE :q")
//    public List<Usuario> findAllByQ(@Param("q") String q);
    
    
    
    
}
