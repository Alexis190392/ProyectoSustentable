package com.trees.treeSave.repositories;

import com.trees.treeSave.Entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<Users, String> {

    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Users findByUsername(@Param("username") String username);
    /*
    @Query("SELECT u FROM Users u WHERE u.documento LIKE :q or u.nombre LIKE :q"
            + " or u.telefono LIKE :q or u.username LIKE :q or u.apellido LIKE :q or u.domicilio LIKE :q")
    public List<Users> findAllByQ(@Param("q") String q);*/
}