//
package com.trees.treeSave.Entity;

import com.trees.treeSave.enumeraciones.Tipo;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Producto {

    @Id
    private String codigoBarra; //paso a Sting ya que no se utiliza para calculos, y para la posibilidad de codigos con letras
   /* @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;*/
    private String nombre;
    private String origen;                // nacional o importado o la provincia - proximo a cambiar por entidad o enum
    // private Integer codigoBarra;          // definir como foreign key  como ¿?
    private Double precio;     
    private Integer stock;    
    @Enumerated(EnumType.STRING)
    private Tipo tipo;                  // enum del tipo de Producto según tipo de huella
    private String urlImg;              //proximo a MIME multipartFile
    private Integer puntos;             // respecto a calculo por huella de carbono
    
    @ManyToOne
    private Categoria categoria;
    
    /*
    @OneToMany(mappedBy = "lista")
    private List<Lista> listas;
    */

    public Producto() {
        this.precio = 0.0d;
        this.stock = 0;
        this.tipo = Tipo.OTROS;
        this.puntos = 0;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    
}
