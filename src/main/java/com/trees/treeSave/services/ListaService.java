package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.ListaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListaService {

    @Autowired
    private ListaRepository lr;
    @Autowired
    private ProductoServicio ps;
    @Autowired
    private ClienteService cs;

    @Transactional
    public Lista save(Lista lista) {
        return lr.save(lista);
    }

    //crear lista
    public Lista create(Lista lista) {
        if (lista.getNombreList() == null) {
            lista.setNombreList("Lista nueva");
        }
        lista.setEstado(true);
        return save(lista);
    }

    public Optional<Lista> findById(String Id) {
        return lr.findById(Id);
    }

    public void cambiarNombre(Lista lista, String nombre) {
        Lista l = findById(lista.getId()).get();
        l.setNombreList(nombre);
        save(l);
    }

    //para cuando se finaliza una lista
    public void cambiarEstado(Lista lista) {
        Lista l = findById(lista.getId()).get();
        if (l.getEstado() == true) {
            l.setEstado(false);
        }
        save(l);
    }

    @Transactional
    public void delete(Lista lista) throws WebException {
        if (lista.getEstado() != false) {
            lr.delete(lista);
        } else {
            throw new WebException("La lista ha sido finalizada, no se puede realizar la eliminacion de la misma");
        }
    }

    public Lista agregarProducto(Lista lista, String sku, Integer cantidad) {
        //para evitar inconvenientes de listas, lo manejo aparte
        Lista actual = findById(lista.getId()).get();
        TreeMap l = actual.getListado();
        //añado el sku del producto y la cantidad, caso de ser cero, se elimina
        if (cantidad > 0) {
            l.put(sku, cantidad);
        } else {
            l.remove(cantidad);
        }
        //seteo en la lista actual
        actual.setListado(l);

        return save(actual);
    }

    //utilidad
    //pasar a TreeMap a List ----- utilizo stock como cantidad para mostrar listado
    public List<Producto> conversion(TreeMap<String, Integer> t) {

        List<Producto> conv = new ArrayList<>();

        if (t != null) {
            t.entrySet().stream().map((m) -> {
                Producto p = ps.searchCod(m.getKey());
                p.setStock(m.getValue());
                return p;
            }).forEachOrdered((p) -> {
                conv.add(p);
            });
        } else{
            conv.add(new Producto());
        }

        return conv;
    }

    //obtener lista como objeto
    public Lista obtenerLista(String documento) throws WebException {
        return findById(cs.findByDocumento(documento).getLista()).get();
    }

}
