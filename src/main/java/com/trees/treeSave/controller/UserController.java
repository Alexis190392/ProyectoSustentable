package com.trees.treeSave.controller;

import com.trees.treeSave.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarUsuarios(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
//            model.addAttribute("usuarios", usuarioServicio.listAllByQ(q));
            model.addAttribute("usuarios", usuarioServicio.listAll());
        } else {
            model.addAttribute("usuarios", usuarioServicio.listAll());
        }

        return "usuario-list";
    }

}