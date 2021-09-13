package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.ClienteRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Fede
 */
@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepository clienteRepository;

    //@Autowired
    //private FotoServicio fotoServicio;
    
     /*@Autowired
    private NotificacionServicio notificacionServicio;*/
    
    @Transactional
    public Cliente save(Cliente cliente, MultipartFile archivo) throws WebException {
        validarCliente(cliente);
        cliente.setAlta(new Date());
        cliente.setNivel(Nivel.SEMILLA);
        cliente.setPuntajeAcumulado(0);
        cliente.setPuntajeCanjeado(0);
        //Foto foto = fotoServicio.guardarFoto(archivo);
        //cliente.setFoto(foto);
        //notificacionServicio.enviar("Bienvenido/a a TreeSave! ", "TreeSave", cliente.getContactoMail());
         return clienteRepository.save(cliente);
    }

    public void validarCliente(Cliente cliente) throws WebException {
        if (findByDocumento(cliente.getDocumento()) != null) {
            throw new WebException("El documento que quieres registrar ya existe.");
        }
        if (cliente.getDocumento() == null) {
            throw new WebException("Debes indicar tu documento.");
        }
        if (cliente.getNombres() == null | cliente.getNombres().isEmpty()) {
            throw new WebException("Debes indicar tu/s nombre/s.");
        }
        if (cliente.getApellido() == null | cliente.getApellido().isEmpty()) {
            throw new WebException("Debes indicar tu apellido.");
        }
        /*if (cliente.getDomicilio() == null | cliente.getDomicilio().isEmpty()) {
            throw new ErrorServicio("El domicilio no puede ser nulo.");
        }*/
        if (cliente.getContactoMail()== null | cliente.getContactoMail().isEmpty()) {
            throw new WebException("El telefono no puede ser nulo.");
        }
    }

    @Transactional
    public void deshabilitarCliente(String id) throws WebException {
        Optional<Cliente> respuesta = clienteRepository.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setBaja(new Date());
            clienteRepository.save(cliente);
        } else {
            throw new WebException("No se encontro el cliente solicitado.");
        }
    }

    @Transactional
    public void habilitarCliente(String id) throws WebException {
        Optional<Cliente> respuesta = clienteRepository.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setBaja(null);
            clienteRepository.save(cliente);
        } else {
            throw new WebException("No se encontro el cliente solicitado.");
        }
    }

    public List<Cliente> listAll() {
        return clienteRepository.findAll();
    }

    /*@Transactional
    public void eliminarPorDocumento(String documento) {
        if (clienteRepository.buscarPorDocumento(documento) != null) {
            clienteRepository.eliminarPorDocumento(documento);
        }
    }*/
    public Optional<Cliente> findById(String id) {
        return clienteRepository.findById(id);
    }

    public Cliente findByDocumento(Long documento) {
        return clienteRepository.buscarPorDocumento(documento);
    }

    public List<Cliente> listAllByQ(String q) {
        return clienteRepository.findAllByQ("%" + q + "%");
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Cliente> optional = clienteRepository.findById(id);
        if (optional.isPresent()) {
            clienteRepository.delete(optional.get());
        }
    }

    @Transactional
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Transactional
    public void modificarCliente(MultipartFile archivo, Cliente cliente) throws WebException {
        //Optional<Cliente> respuesta = clienteRepository.findById(cliente.getId());
        //if (respuesta.isPresent()) {
        //cliente = respuesta.get();
        if (cliente.getDocumento() == null) {
            throw new WebException("El documento no puede ser nulo.");
        }
        if (cliente.getNombres() == null | cliente.getNombres().isEmpty()) {
            throw new WebException("El nombre no puede ser nulo.");
        }
        if (cliente.getApellido() == null | cliente.getApellido().isEmpty()) {
            throw new WebException("El apellido no puede ser nulo.");
        }
        /*if (cliente.getDomicilio() == null | cliente.getDomicilio().isEmpty()) {
            throw new ErrorServicio("El domicilio no puede ser nulo.");
        }*/
        if (cliente.getContactoCel()== null | cliente.getContactoCel().isEmpty()) {
            throw new WebException("El telefono no puede ser nulo.");
        }
        cliente.setNombres(cliente.getNombres());
        cliente.setApellido(cliente.getApellido());
        //cliente.setDomicilio(cliente.getDomicilio());
        cliente.setContactoCel(cliente.getContactoCel());
        cliente.setDocumento(cliente.getDocumento());
       /* String idFoto = null;
        if (cliente.getFoto() != null) {
            idFoto = cliente.getFoto().getId();
        }
        //Foto foto = fs.actualizar(idFoto, archivo);
        //cliente.setFoto(foto);*/
        clienteRepository.save(cliente);
        //} else {
        // throw new ErrorServicio("No se encontro el cliente solicitado.");
    }
}


