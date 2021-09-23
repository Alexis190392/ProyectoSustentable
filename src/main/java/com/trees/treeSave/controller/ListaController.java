package com.trees.treeSave.controller;

import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ListaService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Fede
 */
@Controller
@RequestMapping("/lista")
public class ListaController {

    @Autowired
    private ListaService listaService;

    /*@PostMapping("/agregar")
    public String agregarALista(Model model
            , @RequestParam(required = true) String sku
            , @RequestParam(required = true) String documento
            , @RequestParam(required = false) Integer cantidad) {
        try {
            listaService.agregarALista(sku, documento, cantidad);
        } catch (WebException ex) {
            Logger.getLogger(ListaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("listas", listaService.listAll());
        return "redirect:/producto/list";
    }*/
}
