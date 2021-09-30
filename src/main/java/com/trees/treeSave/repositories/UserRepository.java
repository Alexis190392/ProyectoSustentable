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
