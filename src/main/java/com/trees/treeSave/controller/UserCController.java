package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.UserCliente;
import com.trees.treeSave.services.CiudadService;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UserCController {

    @Autowired
    private UserService userService;

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private ClienteService clienteService;

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listC")
    public String listarClientes(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
//            model.addAttribute("usuarios", usuarioServicio.listAllByQ(q));
            model.addAttribute("usuarios",userService.listAllCliente());
        } else {
            model.addAttribute("usuarios", userService.listAllCliente());
        }

        return "usuario-list";
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listV")
    public String listarVendedores(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
//            model.addAttribute("usuarios", usuarioServicio.listAllByQ(q));
            model.addAttribute("usuarios", userService.listAllVendedor());
        } else {
            model.addAttribute("usuarios", userService.listAllVendedor());
        }

        return "vendedor-list"; //return "vendedor-list.html";
    }

}
