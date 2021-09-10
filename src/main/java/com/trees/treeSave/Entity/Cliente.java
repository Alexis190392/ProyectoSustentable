package com.trees.treeSave.Entity;

import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.enumeraciones.Tipo;
import com.trees.treeSave.enumeraciones.TipoDoc;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente implements Serializable {

    @Id    
    private Long documento; 
    @Enumerated(EnumType.STRING)
    private TipoDoc tipoDoc;

    private String apellido;
    private String nombres;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;           // para saludo y/o promo según rango etario
    
    private Integer puntajeAcumulado;       // Cantidad de Puntos Disponibles
    private Integer puntajeCanjeado;        // Histórico de Puntos
    @Enumerated(EnumType.STRING)
    private Nivel nivel;                    // enum del tipo de cliente, de acuerdo a su historial de compras
    private String listaCompra;             // Lista de compra asociada a la persona
    private String contactoMail;
    private String contactoCel;
    @OneToMany()                            // se eimina el mapped by para usar la Herencia de la linea 25 lo hace automatico
    private List<Lista> listas;
    
}
