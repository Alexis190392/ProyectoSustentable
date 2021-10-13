package com.trees.treeSave.Entity;

import com.trees.treeSave.enumeraciones.Nivel;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Vendedor {
    
    @Id
    private String cuit;
    private String nombre;
    private String domicilio;
    @OneToOne
    private Ciudad ciudad;
    private String contactoMail;
    private String contactoCel;
    @Enumerated(EnumType.STRING)
    private Nivel nivel; 
    @DateTimeFormat(pattern = "yyyy-MM-dd")      //Agrego una fecha de alta y baja de cliente 
    @Temporal(TemporalType.DATE)                 //que luego la heredaría tmb a nivel usuario (usuario extends cliente)
    private Date alta;                           //(new Date en clienteService cuando se hace el save en la db) 
    @DateTimeFormat(pattern = "yyyy-MM-dd")      
    @Temporal(TemporalType.DATE)                 
    private Date baja;  
    
    @OneToOne
    private Foto foto;
    
//    @OneToMany
//    private List<Producto> catalogo;

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

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getContactoMail() {
        return contactoMail;
    }

    public void setContactoMail(String contactoMail) {
        this.contactoMail = contactoMail;
    }

    public String getContactoCel() {
        return contactoCel;
    }

    public void setContactoCel(String contactoCel) {
        this.contactoCel = contactoCel;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }

//    public List<Producto> getCatalogo() {
//        return catalogo;
//    }
//
//    public void setCatalogo(List<Producto> catalogo) {
//        this.catalogo = catalogo;
//    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }


    
    
    
}
