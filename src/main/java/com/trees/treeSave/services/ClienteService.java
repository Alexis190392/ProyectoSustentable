package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Foto;
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
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private CiudadService ciudadService;

    /*@Autowired
    private NotificacionServicio notificacionServicio;*/
    @Transactional
    public Cliente save(Cliente cliente) throws WebException {
        //notificacionServicio.enviar("Bienvenido/a a TreeSave! ", "TreeSave", cliente.getContactoMail());
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void validarCliente(Cliente cliente, MultipartFile file) throws WebException {
        Cliente clienteAlta = new Cliente();
        if (findByDocumento(cliente.getDocumento()) != null) {
            throw new WebException("El documento que quieres registrar ya existe.");
        } else if (cliente.getDocumento() == null) {
            throw new WebException("Debes indicar tu documento.");
        } else {
            clienteAlta.setDocumento(cliente.getDocumento());
        }
        if (cliente.getNombres() == null | cliente.getNombres().isEmpty()) {
            throw new WebException("Debes indicar tu/s nombre/s.");
        } else {
            clienteAlta.setNombres(cliente.getNombres());
        }
        if (cliente.getApellido() == null | cliente.getApellido().isEmpty()) {
            throw new WebException("Debes indicar tu apellido.");
        } else {
            clienteAlta.setApellido(cliente.getApellido());
        }
        if (cliente.getContactoMail() == null | cliente.getContactoMail().isEmpty()) {
            throw new WebException("Debes indicar tu mail.");
        } else {
            clienteAlta.setContactoMail(cliente.getContactoMail());
        }
        if (cliente.getContactoCel() == null | cliente.getContactoCel().isEmpty()) {
            throw new WebException("Debes indicar tu telefono.");
        } else {
            clienteAlta.setContactoCel(cliente.getContactoCel());
        }
        if (cliente.getFechaNacimiento() == null) {
            throw new WebException("Debes indicar tu fecha de nacimiento.");
        } else {
            clienteAlta.setFechaNacimiento(cliente.getFechaNacimiento());
        }
        if (cliente.getCiudad() == null) {
            throw new WebException("Debes indicar tu ciudad.");
        } else {
            clienteAlta.setCiudad(ciudadService.findById(cliente.getCiudad()));
        }
        //FALTA TIPO DOC
        clienteAlta.setAlta(new Date());
        clienteAlta.setBaja(null);
        clienteAlta.setNivel(Nivel.SEMILLA);
        clienteAlta.setPuntajeAcumulado(0);
        clienteAlta.setPuntajeCanjeado(0);

        Foto foto = fotoService.save(file);
        clienteAlta.setFoto(foto);

        save(clienteAlta);
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


    public Optional<Cliente> findById(String id) {
        return clienteRepository.findById(id);
    }

    public Cliente findByDocumento(String documento) throws WebException {
        return clienteRepository.buscarPorDocumento(documento);
    }

    public List<Cliente> listAllByQ(String q) {
        return clienteRepository.findAllByQ("%" + q + "%");
    }

    @Transactional
    public void deleteById(String documento) {
        Optional<Cliente> optional = clienteRepository.findById(documento);
        if (optional.isPresent()) {
            fotoService.delete(optional.get().getFoto());
            clienteRepository.delete(optional.get());
        }
    }

    @Transactional
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Transactional
    public void modificarCliente(MultipartFile archivo, Cliente cliente) throws WebException {
        Cliente clienteBuscado = findById(cliente.getDocumento()).get();
        if (cliente.getDocumento() == null | cliente.getDocumento().isEmpty()) {
            throw new WebException("El documento no puede ser nulo.");
        }
        if (cliente.getNombres() == null | cliente.getNombres().isEmpty()) {
            throw new WebException("El nombre no puede ser nulo.");
        }
        if (cliente.getApellido() == null | cliente.getApellido().isEmpty()) {
            throw new WebException("El apellido no puede ser nulo.");
        }
        if (cliente.getFechaNacimiento() == null) {
            throw new WebException("Debes indicar un telefono.");
        }
        if (cliente.getContactoMail() == null | cliente.getContactoMail().isEmpty()) {
            throw new WebException("Debes indicar un mail.");
        }
        if (cliente.getFechaNacimiento() == null) {
            throw new WebException("Debes indicar tu fecha de nacimiento.");
        }
        if (cliente.getCiudad() == null) {
            throw new WebException("Debes indicar tu ciudad.");
        } else {
            clienteBuscado.setCiudad(ciudadService.findById(cliente.getCiudad()));
        }

        clienteBuscado.setNombres(cliente.getNombres());
        clienteBuscado.setApellido(cliente.getApellido());
        clienteBuscado.setContactoCel(cliente.getContactoCel());
        clienteBuscado.setDocumento(cliente.getDocumento());
        clienteBuscado.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteBuscado.setContactoMail(cliente.getContactoMail());

        clienteBuscado.setAlta(clienteBuscado.getAlta());
        clienteBuscado.setBaja(null);
        clienteBuscado.setNivel(clienteBuscado.getNivel());
        clienteBuscado.setPuntajeAcumulado(clienteBuscado.getPuntajeAcumulado());
        clienteBuscado.setPuntajeCanjeado(clienteBuscado.getPuntajeCanjeado());

        String idFoto = null;
        if (cliente.getFoto() != null) {
            idFoto = cliente.getFoto().getId();
        }
        Foto foto = fotoService.actualizar(idFoto, archivo);
        clienteBuscado.setFoto(foto);

        clienteRepository.save(clienteBuscado);

    }

    public List<Cliente> listAllByCiudad(String nombre) {
        return clienteRepository.findAllByCiudad(nombre);
    }

    @Transactional
    public void deleteFieldCiudad(Ciudad ciudad) {
        List<Cliente> clientes = listAllByCiudad(ciudad.getNombre());
        for (Cliente cliente : clientes) {
            cliente.setCiudad(null);
        }
        clienteRepository.saveAll(clientes);
    }
}
