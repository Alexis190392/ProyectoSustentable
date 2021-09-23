
package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.CiudadRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadService {
    
    @Autowired
    private CiudadRepository ciudadRepository;
    
    @Autowired
    private ClienteService clienteServicio;
    
    public Ciudad save (String nombre){
       Ciudad ciudad = new Ciudad ();
       ciudad.setNombre(nombre);
       return ciudadRepository.save(ciudad);
    }
    public Ciudad save (Ciudad ciudad) throws WebException{
        if (ciudad.getNombre() == null){
            throw new WebException("El nombre de la ciudad no puede ser nulo");
        }
        return ciudadRepository.save(ciudad);
    }

    public List<Ciudad> listAll() {
        return ciudadRepository.findAll();
    }
    public List<Ciudad> listAll(String q) {
        return ciudadRepository.findAll("%"+q+"%");
    }
    
    //para validar que la ciudad no este repetida
    public Ciudad findById(Ciudad ciudad) {
        Optional<Ciudad>optional = ciudadRepository.findById(ciudad.getId());
            if(optional.isPresent()){
                ciudad=optional.get();
            }
        return ciudad;
    }
    
    public Optional <Ciudad> findById(String id){
        return ciudadRepository.findById(id);
    }
    
    @Transactional
    public void delete (Ciudad ciudad){
        ciudadRepository.delete(ciudad);
    }
    
    
    @Transactional
    public void deleteById ( String id){
        Optional<Ciudad> optional = ciudadRepository.findById(id);
        if(optional.isPresent()){
            Ciudad ciudad = optional.get();
           //clienteServicio.deleteFieldCiudad(ciudad);
            ciudadRepository.delete(ciudad);
            
        }
    }
}
