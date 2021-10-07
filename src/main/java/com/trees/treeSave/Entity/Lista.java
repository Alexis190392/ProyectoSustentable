//
package com.trees.treeSave.Entity;

import java.io.Serializable;
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
    @OneToMany
    private List<Producto> productos;

    public Lista() {
    }

    public String getNombreList() {
        return nombreList;
    }

    public void setNombreList(String nombreList) {
        this.nombreList = nombreList;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
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

    

