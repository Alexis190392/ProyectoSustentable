package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.ListaRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fede
 */
@Service
public class ListaService {
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private ListaRepository listaRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @Transactional
    public Lista save(Lista lista) throws WebException {
        return listaRepository.save(lista);
    }
    
    /*private void validarYAsignar(Lista lista, Cliente cliente) throws WebException { //El metodo recibe una lista y un id cliente,
        if (lista.getNombre() == null) {                                              //valido nombre  
            throw new WebException("La lista debe tener nombre");
        }                                                                             //Se guarda la lista de prod 
        cliente.setListaMadre(cliente.getListaMadre());
        clienteService.save(cliente);                                                 //guardo cliente con su lista
    }*/
    
    @Transactional
    public Lista agregarALista(Lista lista, String sku, String documento, Integer cantidad) throws WebException {  //metodo que agrega productos a la lista
        
        Producto producto = productoService.searchCod(sku);
        Cliente cliente = clienteService.findByDocumento(documento);
        
        lista.getProductos().add(producto);
        lista.setProductos(lista.getProductos());
        //validarYAsignar(lista, cliente);
        return listaRepository.save(lista);

        /*      Optional<Lista> optional = findById(idLista);
        if (optional.isPresent()) {
            Producto producto = productoService.searchCod(codigoBarra);

            optional.get().getProductos().add(producto);

            validarYAsignar(optional.get(), documento);
            return listaRepository.save(optional.get());
        } else {
            throw new WebException("No se encontró la lista");
        }*/        
    }
    
    @Transactional
    public void delete(Lista lista) {
        listaRepository.delete(lista);
    }
    
    @Transactional
    public Lista eliminarDeLista(Lista lista, String codigoBarra) {     //Elimina un prod de la lista
        Producto producto = productoService.searchCod(codigoBarra);
        lista.getProductos().remove(producto);
        return listaRepository.save(lista);
    }
    
    public List<Lista> listAll() {
        return listaRepository.findAll();
    }
    
    public Optional<Lista> findById(String id) {
        return listaRepository.findById(id);
    }
}
