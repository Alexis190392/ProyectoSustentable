/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trees.treeSave.services;

import com.trees.treeSave.Entity.User;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ivan Doom Days
 */
@Service
public class UserService {

    @Autowired//para llamar al UserRepository
    private UserRepository userRepository; //creo obj de tipo UserRepo..

    public User save(String username, String password, String password2) throws WebException { //recibimos en save ,paso 2 contraseñas para compararlas
        User user = new User();
        if (username.isEmpty() || username == null) { // usuario ni vacio ni nulo
            throw new WebException("El usuario no puede estar vacio");// aderimos la clausula para usar WebException 
        }
        if (password.isEmpty() || password == null || password2.isEmpty() || password2.isEmpty()) { //password ni vacio ni nulo
            throw new WebException("El contraseña no puede estar vacio");// aderimos la clausula para usar WebException 
            }
         if (!password.equals(password2)) { //comparo las contraseñas , uso !password para para saber si NO son iguales.
            throw new WebException("Las contraseñas no son iguales");
            }
         user.setusername(username);
         user.setPassword(password);// se deja simple aun, para configurar despuescon Bcry

        return userRepository.save(user);   //tener cuidado cuando pongo el return, por hay lo ponemos en la llave de abajo.
    }

}
