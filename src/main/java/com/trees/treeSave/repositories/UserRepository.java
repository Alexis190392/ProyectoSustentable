<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trees.treeSave.repositories;
import com.trees.treeSave.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan Doom Days
 */
@Repository
public interface UserRepository extends  JpaRepository < User , String>{//la entidad User no tiene un ID en su clase,
    //pero JPA entiende que la hereda de Cliente.
    
}
=======
package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<Users, String> {

    @Query("SELECT u FROM Users u WHERE u.username = :username or u.contactoMail = :username")
    Users findByUsernameOrMail(@Param("username") String username);
    /*
    @Query("SELECT u FROM Users u WHERE u.documento LIKE :q or u.nombre LIKE :q"
            + " or u.telefono LIKE :q or u.username LIKE :q or u.apellido LIKE :q or u.domicilio LIKE :q")
    public List<Users> findAllByQ(@Param("q") String q);*/
}
>>>>>>> 777bc056c8ce2e372019f2f2510fea93eef74028
