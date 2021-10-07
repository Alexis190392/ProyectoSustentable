package com.trees.treeSave.Entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Vendedor {
    
    @Id
    private String cuit;
    private String nombre;
    private String domicilio;
    private String contacto;
    private String horario;
    @OneToMany
    private List<Producto> catalogo;


    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public List<Producto> getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(List<Producto> catalogo) {
        this.catalogo = catalogo;
    }
    
}
