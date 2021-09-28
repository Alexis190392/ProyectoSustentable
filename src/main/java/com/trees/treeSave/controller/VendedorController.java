package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Vendedor;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.VendedorServicio;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorServicio vendedorServicio;

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarVendedores(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("vendedores", vendedorServicio.listAllByQ(q));
        } else {
            model.addAttribute("vendedores", vendedorServicio.listAll());
        }
        return "vendedor-list";
    }

    @GetMapping("/form")
    public String crearVendedor(Model model, @RequestParam(required = false) String id, @RequestParam(required = false) String action) {
        if (id != null) {
            Optional<Vendedor> optional = vendedorServicio.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("vendedor", optional.get());
                model.addAttribute("action", action);
            } else {
                return "vendedor-list";
            }
        } else {
            model.addAttribute("vendedor", new Vendedor());
            model.addAttribute("action", action);
        }
        return "vendedor-form";
    }

    @GetMapping("/delete")
    public String eliminarVendedor(@RequestParam(required = true) String id) {
        vendedorServicio.deleteById(id);
        return "redirect:/vendedor/list";
    }

    @PostMapping("/save")
    public String guardarVendedor(Model model, RedirectAttributes redirectAttributes,
            @ModelAttribute Vendedor vendedor, @RequestParam(required = true) String action) throws WebException {
        try {
            //if(action.equals("crear")) {
            //     cs.save(vendedor, null);
            if (action.equals("edit")) {
                vendedorServicio.modificarVendedor(vendedor);
                redirectAttributes.addFlashAttribute("success", "Vendedor modificado con éxito.");
            } else {
                vendedorServicio.validarVendedor(vendedor);
                redirectAttributes.addFlashAttribute("success", "Cliente guardado con éxito.");
            }

        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("vendedor", vendedor);
            return "vendedor-form";
        }
        return "redirect:/vendedor/list";
    }

}
