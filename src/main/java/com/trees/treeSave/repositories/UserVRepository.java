
package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.UserVendedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVRepository  extends JpaRepository<UserVendedor, String> {

    @Query("SELECT u FROM UserVendedor u WHERE u.username = :username or u.contactoMail = :username")
    UserVendedor findByUsernameOrMail(@Param("username") String username);
    /*
    @Query("SELECT u FROM UserVendedor u WHERE u.documento LIKE :q or u.nombre LIKE :q"
            + " or u.telefono LIKE :q or u.username LIKE :q or u.apellido LIKE :q or u.domicilio LIKE :q")
    public List<Users> findAllByQ(@Param("q") String q);*/
}
