package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Vendedor;
import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.VendedorRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendedorServicio {

    @Autowired
    private VendedorRepository vr;
    @Autowired
    private CiudadService ciudadService;

     @Transactional
    public Vendedor save(Vendedor vendedor) throws WebException {
        
        return vr.save(vendedor);
    }
    
    @Transactional
    public void validarVendedor(Vendedor v) throws WebException {
        //validaciones
        Vendedor vendedorAlta = new Vendedor();
        
        if (findByCuit(v.getCuit()) != null) {
            throw new WebException("El cuit que quieres registrar ya existe.");
        } else if (v.getCuit() == null) {
            throw new WebException("Debes indicar tu numero de cuit.");
        } else {
            vendedorAlta.setCuit(v.getCuit());
        }
        
        if (v.getNombre().isEmpty() || v.getNombre() == null) {
            throw new WebException("El nombre no puede estar vacio");
        }else{
            vendedorAlta.setNombre(v.getNombre());
        }
        
        if (v.getDomicilio().isEmpty() || v.getDomicilio() == null) {
            throw new WebException("El domicilio no puede estar vacio");
        }else{
            vendedorAlta.setDomicilio(v.getDomicilio());
        }
        
        if (v.getCiudad() == null) {
            throw new WebException("Ls ciudad no puede ser nula");
        } else {
            v.setCiudad(ciudadService.findById(v.getCiudad()));
        }
        
        if (v.getContactoMail().isEmpty() || v.getContactoMail()== null) {
            throw new WebException("El mail no puede estar vacio");
        }else{
            vendedorAlta.setContactoMail(v.getContactoMail());
        }
        
        if (v.getContactoCel().isEmpty() || v.getContactoCel()== null) {
            throw new WebException("El numero de celular no puede estar vacio");
        }else{
            vendedorAlta.setContactoCel(v.getContactoCel());
        }
        
        vendedorAlta.setAlta(new Date());
        vendedorAlta.setBaja(null);
        vendedorAlta.setNivel(Nivel.SEMILLA);
        save(vendedorAlta); 
       
    }
    
    @Transactional
    public void deshabilitarVendedor(String id) throws WebException {
        Optional<Vendedor> respuesta = vr.findById(id);
        if (respuesta.isPresent()) {
            Vendedor vendedor = respuesta.get();
            vendedor.setBaja(new Date());
            vr.save(vendedor);
        } else {
            throw new WebException("No se encontró el vendedor solicitado");
        }
    }
    
    @Transactional
    public void habilitarCliente(String id) throws WebException {
        Optional<Vendedor> respuesta = vr.findById(id);
        if (respuesta.isPresent()) {
            Vendedor vendedor = respuesta.get();
            vendedor.setBaja(null);
            vr.save(vendedor);
        } else {
            throw new WebException("No se encontró el cliente solicitado.");
        }
    }

    public List<Vendedor> listAll() {
        return vr.findAll();
    }

    //buscador general
    public List<Vendedor> listAllByQ(String query) {
        return vr.findId("%" + query + "%");
    }

    public List<Vendedor> listAllbyCiudad(String nombre) {
        return vr.findAllByCiudad(nombre);
    }

    public Optional<Vendedor> findById(String id) {
        return vr.findById(id);
    }

   public Vendedor findByCuit (String cuit){
       return vr.findByCuit(cuit);
 }

    /*   ELIMINAR     */
    @Transactional
    public void delete(Vendedor v) {
        vr.delete(v);
    }

//    @Transactional
//    public void deleteFieldCiudad(Ciudad ciudad) {
//        List<Vendedor> vendedores = listAllbyCiudad(ciudad.getNombre());
//        for (Vendedor vendedor : vendedores) {
//            vendedor.setCiudad(null);
//        }
//        vr.saveAll(vendedores);
//    }

    @Transactional
    public void deleteById(String cuit) {
        Optional<Vendedor> op = vr.findById(cuit);
        if (op.isPresent()) {
            vr.delete(op.get());
        }
    }

    @Transactional
    public void modificarVendedor(Vendedor vendedor) throws WebException {
        if (vendedor.getCuit() == null) {
            throw new WebException("El cuit no puede ser nulo.");
        }
        if (vendedor.getNombre() == null) {
            throw new WebException("El nombre no puede ser nulo.");
        }
        if (vendedor.getDomicilio() == null | vendedor.getDomicilio().isEmpty()) {
            throw new WebException("El domicilio no puede ser nulo.");
        }
        if (vendedor.getCiudad() == null) {
            throw new WebException("El vendedor no puede ser nulo.");
        }
        if (vendedor.getContactoMail().isEmpty() || vendedor.getContactoMail()== null) {
            throw new WebException("El mail no puede estar vacio");
        }
        if (vendedor.getContactoCel().isEmpty() || vendedor.getContactoCel()== null) {
            throw new WebException("El numero de celular no puede estar vacio");
        }
        
        vendedor.setCuit(vendedor.getCuit());
        vendedor.setNombre(vendedor.getNombre());
        vendedor.setDomicilio(vendedor.getDomicilio());
        vendedor.setCiudad(vendedor.getCiudad());
        vendedor.setContactoMail(vendedor.getContactoMail());
        vendedor.setContactoCel(vendedor.getContactoCel());
        vr.save(vendedor);

    }
}
