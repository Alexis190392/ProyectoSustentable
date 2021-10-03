package com.trees.treeSave.RegistroCliente;

import com.trees.treeSave.Entity.Foto;
import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.enumeraciones.Role;
import static com.trees.treeSave.enumeraciones.Role.USER;
import com.trees.treeSave.enumeraciones.TipoDoc;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Usuario {
    
    
    /* principales datos para registro */
    @Id
    private String documento;
    
    private String username;
    private String password;
    private final Role role= USER;
    
    
    @Enumerated(EnumType.STRING)
    private TipoDoc tipoDoc;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")      //Agrego una fecha de alta y baja de cliente 
    @Temporal(TemporalType.DATE)                 //que luego la heredaría tmb a nivel usuario (usuario extends cliente)
    private Date alta;                           //(new Date en clienteService cuando se hace el save en la db) 
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")      
    @Temporal(TemporalType.DATE)                 
    private Date baja;         
    
    /*  datos personales */
    private String apellido;
    private String nombres;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;           // para saludo y/o promo según rango etario
    private String contactoMail;
    private String contactoCel;
    
    
    /* HC y lista */
    private Integer puntajeAcumulado;       // Cantidad de Puntos Disponibles
    private Integer puntajeCanjeado;        // Histórico de Puntos
    @Enumerated(EnumType.STRING)
    private Nivel nivel;                    // enum del tipo de cliente, de acuerdo a su historial de compras
    private String listaCompra;             // Lista de compra asociada a la persona
    
    @OneToMany()                            // se eimina el mapped by para usar la Herencia de la linea 25 lo hace automatico
    private List<Lista> listas;
    
    @OneToOne
    private Foto foto;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public Integer getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    public void setPuntajeAcumulado(Integer puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }

    public Integer getPuntajeCanjeado() {
        return puntajeCanjeado;
    }

    public void setPuntajeCanjeado(Integer puntajeCanjeado) {
        this.puntajeCanjeado = puntajeCanjeado;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(String listaCompra) {
        this.listaCompra = listaCompra;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }    
}
