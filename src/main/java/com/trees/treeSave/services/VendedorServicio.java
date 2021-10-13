package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Foto;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class VendedorServicio {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private CiudadService ciudadService;

    @Transactional
    public Vendedor save(Vendedor vendedor) throws WebException {
        return vendedorRepository.save(vendedor);
    }

    @Transactional
    public void validarVendedor(Vendedor v, MultipartFile file) throws WebException {
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
        } else {
            vendedorAlta.setNombre(v.getNombre());
        }

        if (v.getDomicilio().isEmpty() || v.getDomicilio() == null) {
            throw new WebException("El domicilio no puede estar vacio");
        } else {
            vendedorAlta.setDomicilio(v.getDomicilio());
        }

        if (v.getCiudad() == null) {
            throw new WebException("Ls ciudad no puede ser nula");
        } else {
            vendedorAlta.setCiudad(ciudadService.findById(v.getCiudad()));
        }

        if (v.getContactoMail().isEmpty() || v.getContactoMail() == null) {
            throw new WebException("El mail no puede estar vacio");
        } else {
            vendedorAlta.setContactoMail(v.getContactoMail());
        }
        if (v.getContactoCel().isEmpty() || v.getContactoCel() == null) {
            throw new WebException("El numero de celular no puede estar vacio");
        } else {
            vendedorAlta.setContactoCel(v.getContactoCel());
        }

        Foto foto = fotoService.save(file);
        vendedorAlta.setFoto(foto);

        vendedorAlta.setAlta(new Date());
        vendedorAlta.setBaja(null);
        vendedorAlta.setNivel(Nivel.SEMILLA);
        save(vendedorAlta);

    }

    @Transactional
    public void deshabilitarVendedor(String id) throws WebException {
        Optional<Vendedor> respuesta = vendedorRepository.findById(id);
        if (respuesta.isPresent()) {
            Vendedor vendedor = respuesta.get();
            vendedor.setBaja(new Date());
            vendedorRepository.save(vendedor);
        } else {
            throw new WebException("No se encontró el vendedor solicitado");
        }
    }

    @Transactional
    public void habilitarVendedor(String id) throws WebException {
        Optional<Vendedor> respuesta = vendedorRepository.findById(id);
        if (respuesta.isPresent()) {
            Vendedor vendedor = respuesta.get();
            vendedor.setBaja(null);
            vendedorRepository.save(vendedor);
        } else {
            throw new WebException("No se encontró el cliente solicitado.");
        }
    }

    public List<Vendedor> listAll() throws WebException {
        return vendedorRepository.findAll();
    }

    //buscador general
    public List<Vendedor> listAllByQ(String query) throws WebException {
        return vendedorRepository.findAllByQ("%" + query + "%");
    }

    public List<Vendedor> listAllbyCiudad(String nombre) throws WebException {
        return vendedorRepository.findAllByCiudad(nombre);
    }

    public Optional<Vendedor> findById(String id) throws WebException {
        return vendedorRepository.findById(id);
    }

    public Vendedor findByCuit(String cuit) throws WebException {
        return vendedorRepository.findByCuit(cuit);
    }

    /*   ELIMINAR     */
    @Transactional
    public void delete(Vendedor v) throws WebException {
        vendedorRepository.delete(v);
    }

    @Transactional
    public void deleteFieldCiudad(Ciudad ciudad) throws WebException {
        List<Vendedor> vendedores = listAllbyCiudad(ciudad.getNombre());
        for (Vendedor vendedor : vendedores) {
            vendedor.setCiudad(null);
        }
        vendedorRepository.saveAll(vendedores);
    }

    @Transactional
    public void deleteById(String cuit) throws WebException {
        Optional<Vendedor> op = vendedorRepository.findById(cuit);
        if (op.isPresent()) {
            vendedorRepository.delete(op.get());
        }
    }

    @Transactional
    public void modificarVendedor(MultipartFile archivo, Vendedor vendedor) throws WebException {
        Vendedor vendedorBuscado = findById(vendedor.getCuit()).get();

        if (vendedor.getCuit().isEmpty()) {
            throw new WebException("Debes indicar tu cuit.");
        } else {
            vendedorBuscado.setCuit(vendedor.getCuit());
        }
        if (vendedor.getNombre().isEmpty()) {
            throw new WebException("Debes indicar tu/s nombre/s.");
        } else {
            vendedorBuscado.setNombre(vendedor.getNombre());
        }
        if (vendedor.getDomicilio().isEmpty()) {
            throw new WebException("Debes indicar tu domicilio.");
        } else {
            vendedorBuscado.setDomicilio(vendedor.getDomicilio());
        }
        if (vendedor.getCiudad() == null) {
            throw new WebException("Debes indicar tu ciudad.");
        } else {
            vendedorBuscado.setCiudad(vendedor.getCiudad());
        }
        if (vendedor.getContactoMail().isEmpty()) {
            throw new WebException("Debes indicar tu E-mail.");
        } else {
            vendedorBuscado.setContactoMail(vendedor.getContactoMail());
        }
        if (vendedor.getContactoCel().isEmpty()) {
            throw new WebException("Debes indicar tu numero de celular.");
        } else {
            vendedorBuscado.setContactoCel(vendedor.getContactoCel());
        }
        
        String idFoto = null;
        if (vendedor.getFoto() != null) {
            idFoto = vendedor.getFoto().getId();
        }
        Foto foto = fotoService.actualizar(idFoto, archivo);
        vendedorBuscado.setFoto(foto);
        
        vendedorRepository.save(vendedorBuscado);
        
    }
}

