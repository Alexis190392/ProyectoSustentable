package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UserService usuarioServicio;
    @Autowired
    private ClienteService cs;

    @GetMapping("")
    public String registro(Model model, @RequestParam String id) throws WebException {
        if(id != null){
            Cliente c = cs.findByDocumento(id);
            model.addAttribute("documento", c.getDocumento());
        }
        return "crear-usuario";
    }
    
 
    @PostMapping("")
    public String registroSave(Model model, @RequestParam String username, 
            @RequestParam String password, @RequestParam String password2, @RequestParam(required=true) String documento) {
        try {
            usuarioServicio.save(username, password, password2, documento);
            return "redirect:/cliente/panel";
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("username", username);
        }
        return "redirect:/";
    }

}