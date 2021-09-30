/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trees.treeSave.Entity;

import javax.persistence.Entity;

/**
 * 
 * @author Ivan Doom Days
 */
@Entity
public class UsuarioV extends Cliente{ //la extiendo de Cliente para  heredar datos con enjoyned
    
private String username;
private String password;

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
