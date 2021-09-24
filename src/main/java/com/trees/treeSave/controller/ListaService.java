package com.trees.treeSave.controller;


import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import org.springframework.stereotype.Service;

@Service
public class ListaService {
    
    //ingresar nombre a la lista
    public Lista crearLista(String nombre){
        Lista lista = new Lista();
        lista.setNombreList(nombre);
        return lista;
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
    
}
