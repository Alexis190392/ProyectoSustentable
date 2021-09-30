package com.trees.treeSave.controller;

import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.UserService;
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

    @GetMapping("")
    public String registro() {
        return "registro";
    }

    @PostMapping("")
    public String registroSave(Model model, @RequestParam String username, 
            @RequestParam String clave, @RequestParam String clave2, @RequestParam String documento) {
        try {
            usuarioServicio.save(username, clave, clave2, documento);
            return "redirect:/";
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("username", username);
        }
        return "registro";
    }

}