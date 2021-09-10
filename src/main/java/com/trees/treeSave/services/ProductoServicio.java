package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.enumeraciones.Tipo;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.ProductoRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicio {
    @Autowired
    private ProductoRepository pr;
    
    //para comunicarse con la bd
    @Transactional
    public Producto save(String codigoBarra,String nombre, String origen,Double precio,
                         Integer stock, Tipo tipo, Integer puntos){
        Producto p = new Producto();
        p.setCodigoBarra(codigoBarra);
        p.setNombre(nombre);
        p.setOrigen(origen);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setTipo(tipo);
        p.setPuntos(puntos);
        
        return pr.save(p);
    }
    
    @Transactional
    public Producto save(Producto p) throws WebException{
        //validaciones
        if(p.getCodigoBarra().isEmpty() || p.getCodigoBarra() == null){
            throw new WebException("El codigo de barras no puede estar vacio.");
        }
        if(p.getNombre().isEmpty() || p.getNombre()== null){
            throw new WebException("El nombre no puede estar vacio.");
        }
        if(p.getPrecio() < 0.0d){
            throw new WebException("El precios no puede ser negativo.");
        }
        if(p.getStock() < 0){  //para que pueda agregar un producto futuro a estar en stock
            throw new WebException("El stock no puede ser negativo.");
        }
        if(p.getTipo()== null){
            throw new WebException("Debe elegir un tipo de producto");
        }
        if(p.getOrigen() == null){
            throw new WebException("Debe elegir un origen.");
        }
        if(p.getPuntos() < 0){
            throw new WebException("Los puntos no pueden ser negativos.");
        }
                
        return pr.save(p);
    }
    
    public List<Producto> listAll(){
        return pr.findAll();
    }
    
    //buscador general
    public List<Producto> listByQuery(String query) {
        return pr.searchId("%" + query + "%");
    }
    
    //buscador por codigo de barras como id
    
    public Optional<Producto> searchCod(String codigo){
        return pr.findById(codigo);
    }
    
    
    /*   ELIMINAR     */
    
    @Transactional
    public void delete(Producto p){
        pr.delete(p);
    }
    
    @Transactional
    public void deleteByCod(String codigoBarra){
        Optional<Producto> op = pr.findById(codigoBarra);
        if(op.isPresent()){
            pr.delete(op.get());
        }
    }
}
