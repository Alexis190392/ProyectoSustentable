package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.ProductoServicio;
import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Fede
 */
@Controller
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoServicio productoService;

    @GetMapping("/cliente")
    public ResponseEntity<byte[]> fotoCliente(@RequestParam(required = true) String id) {

        try {
            Cliente cliente = clienteService.findByDocumento(id);

            if (cliente.getFoto() == null) {
                throw new WebException("El usuario no tiene una foto asignada.");
            }

            byte[] foto = cliente.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (WebException ex) {
            java.util.logging.Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/producto")
    public ResponseEntity<byte[]> fotoProducto(@RequestParam(required = true) String id) {

        try {
            Producto p = productoService.searchCod(id);

            if (p.getFoto() == null) {
                throw new WebException("El usuario no tiene una foto asignada.");
            }

            byte[] foto = p.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (WebException ex) {
            java.util.logging.Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
