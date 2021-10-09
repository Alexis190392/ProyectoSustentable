package com.trees.treeSave.services;


import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.ListaRepository;
import java.util.List;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ListaService {
    @Autowired
    ListaRepository lr;
    
     
    //ingresar nombre a la lista
    public Lista crearLista(String nombre){
        Lista lista = new Lista();
        lista.setNombreList(nombre);
        
        return lr.save(lista);
    }
    
    public Lista crearLista(Lista lista){
        return lr.save(lista);
    }
    
    //ingresar producto a la lista
    public Lista ingresarProducto(Lista lista, Producto p, Integer cant) throws WebException{
        
        if(lista.getListado().containsKey(p.getCodigoBarra())){
            if(cant == 0){
                lista.getListado().remove(p.getCodigoBarra());
            } else{
                lista.getListado().put(p.getCodigoBarra(), cant);
                throw new WebException("Se modifico la cantidad del producto");
            }
        }
        
        return lista;
    }
    
    
    public List<Lista> listAll(){
        
        List<Lista> l =  lr.findAll();
        return l;
    }

    public void deleteById(String id) {
        Optional<Lista> l = lr.findById(id);
        if(l.isPresent()){
            Lista lista = l.get();
            lr.delete(lista);
        }
    }
    
}
