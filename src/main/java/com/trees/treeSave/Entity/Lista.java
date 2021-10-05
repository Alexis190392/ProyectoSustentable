//
package com.trees.treeSave.Entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author aconvertini
 */
@Entity
// @Inheritance(strategy = InheritanceType.JOINED)
public class Lista implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombreList;
    //KEY: id_producto 
    private HashMap<String,Integer> listado;

    private Boolean estado;


    public Lista() {
    }

    public Lista(String id, String nombreList, HashMap<String, Integer> listado) {
        this.id = id;
        this.nombreList = nombreList;
        this.listado = listado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreList() {
        return nombreList;
    }

    public void setNombreList(String nombreList) {
        this.nombreList = nombreList;
    }

    public HashMap<String, Integer> getListado() {
        return listado;
    }

    public void setListado(HashMap<String, Integer> listado) {
        this.listado = listado;

    }

    

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    

}

    

