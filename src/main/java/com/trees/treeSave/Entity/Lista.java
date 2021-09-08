package com.trees.treeSave.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author aconvertini
 */
@Entity
public class Lista implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombreList;    
    @OneToMany(mappedBy = "producto")
    private List<Producto> productos;

    public Lista() {
    }

    public Lista(String id, String nombre) {
        this.id = id;
        this.nombreList = nombre;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombreList;
    }

    public void setNombre(String nombre) {
        this.nombreList = nombre;
    }

    @Override
    public String toString() {
        return "Autor{" + "id=" + id + ", nombre=" + nombreList + '}';
    }


    }

    

