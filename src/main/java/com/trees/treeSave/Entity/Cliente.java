package com.trees.treeSave.Entity;

import com.trees.enumeraciones.Tipo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String apellido;
    private String nombres;
    private Integer documento;              // definir como foreign key  como ¿?
    private LocalDate fechaNacimiento;      // para saludo y/o promo según rango etario
    private Integer puntajeAcumulado;       // Cantidad de Puntos Disponibles
    private Integer puntajeCanjeado;        // Histórico de Puntos
    @Enumerated(EnumType.STRING)
    private Tipo tipo;                      // enum del tipo de cliente, de acuerdo a su historial de compras
    private String listaCompra;             // Lista de compra asociada a la persona
    private String contactoMail;
    private String contactoCel;

    @OneToMany(mappedBy = "cliente")
    private List<Lista> listas;
    
}
