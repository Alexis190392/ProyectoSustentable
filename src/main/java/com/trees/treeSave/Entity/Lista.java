//
package com.trees.treeSave.Entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author aconvertini
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Lista implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombreList;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Producto> productos;  //String sku, integer cantidad de prod a pedir


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

}
