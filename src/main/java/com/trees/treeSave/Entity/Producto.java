package com.trees.treeSave.Entity;

import com.trees.enumeraciones.Tipo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Producto implements Serializable {

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
    
    
}
