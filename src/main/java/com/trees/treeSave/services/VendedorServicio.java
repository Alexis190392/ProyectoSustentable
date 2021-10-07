package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Vendedor;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.VendedorRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendedorServicio {
    
    @Autowired
    private VendedorRepository vr;
    
    //para comunicarse con la bd
    @Transactional
    public Vendedor save(String cuit, String nombre,String domicilio, String contacto, String horario){
        Vendedor v = new Vendedor();
        v.setCuit(cuit);
        v.setNombre(nombre);
        v.setDomicilio(domicilio);
        v.setContacto(contacto);
        v.setHorario(horario);
        
        return vr.save(v);
    }
    
    @Transactional
    public Vendedor save(Vendedor v) throws WebException{
        //validaciones
        if(v.getCuit().isEmpty() || v.getCuit() == null){
            throw new WebException("El CUIT no puede estar vacio");
        }
        if(v.getNombre().isEmpty() || v.getNombre() == null){
            throw new WebException("El nombre no puede estar vacio");
        }
        if(v.getDomicilio().isEmpty() || v.getDomicilio() == null){
            throw new WebException("El domicilio no puede estar vacio");
        }
        if(v.getContacto().isEmpty() || v.getContacto() == null){
            throw new WebException("El domicilio no puede estar vacio");
        }
        if(v.getHorario().isEmpty() || v.getHorario() == null){
            throw new WebException("El domicilio no puede estar vacio");
        }
        
        return vr.save(v);
    }
    
    public List<Vendedor> listAll(){
        return vr.findAll();
    }
    
    //buscador general
    public List<Vendedor> listByQuery(String query) {
        return vr.searchId("%" + query + "%");
    }
    
    /*   ELIMINAR     */
    
    
    @Transactional
    public void delete(Vendedor v){
        vr.delete(v);
    }
    
    @Transactional
    public void deleteById(String cuit){
        Optional<Vendedor> op = vr.findById(cuit);
        if(op.isPresent()){
            vr.delete(op.get());
        }
    }
}
