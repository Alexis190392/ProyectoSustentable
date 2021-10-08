package com.trees.treeSave.controller;


import com.trees.treeSave.services.UserVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuarioVendedor")
public class UserVController {

    @Autowired
    private UserVService usuarioVServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarVendedores(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
//            model.addAttribute("usuarios", usuarioServicio.listAllByQ(q));
            model.addAttribute("usuarios", usuarioVServicio.listAll());
        } else {
            model.addAttribute("usuarios", usuarioVServicio.listAll());
        }

        return "vendedor-list"; //return "vendedor-list.html";
    }

}