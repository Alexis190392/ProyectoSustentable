package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Fede
 */
@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarClientes(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("clientes", clienteService.listAllByQ(q));
        } else {
            model.addAttribute("clientes", clienteService.listAll());
        }
        return "cliente-list";
    }

    @GetMapping("/form")
    public String crearCliente(Model model, @RequestParam(required = false) String id, @RequestParam(required = false) String action) {
        if (id != null) {
            Optional<Cliente> optional = clienteService.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("cliente", optional.get());
                model.addAttribute("action", action);
            } else {
                return "cliente-list";
            }
        } else {
            model.addAttribute("cliente", new Cliente());
            model.addAttribute("action", action);
        }
        return "cliente-form";
    }

    @GetMapping("/deleteTH")
    public String eliminarCliente(@RequestParam(required = true) String id) {
        clienteService.deleteById(id);
        return "redirect:/cliente/list";
    }

    @PostMapping("/save")
    public String guardarCliente(Model model, @RequestParam(required = true) MultipartFile archivo, RedirectAttributes redirectAttributes,
            @ModelAttribute Cliente cliente, @RequestParam(required = true) String action) throws WebException {
        try {
            if (action.equals("edit")) {
                clienteService.modificarCliente(archivo, cliente);
                redirectAttributes.addFlashAttribute("success", "Cliente modificado con éxito.");
            } else {
                clienteService.validarCliente(cliente, archivo);
                redirectAttributes.addFlashAttribute("success", "Cliente guardado con éxito.");
            }

        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("cliente", cliente);
            return "cliente-form";
        }
        return "redirect:/cliente/list";
    }

}
