package com.trees.treeSave.Entity;

import com.trees.treeSave.enumeraciones.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Fede
 */
@Entity
public class Usuario extends Cliente {

    private String username;
    private String clave;
    @Enumerated(EnumType.STRING)
    private Role rol;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

}

