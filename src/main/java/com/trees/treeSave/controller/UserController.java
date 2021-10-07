package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Users;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService usuarioServicio;

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private ClienteService clienteService;

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

    @GetMapping("/editar-perfil")
    public String editarPerfil(@RequestParam String documento, ModelMap model) {

        List<Ciudad> ciudades = ciudadService.listAll();
        model.put("ciudades", ciudades);

        try {
            Cliente cliente = clienteService.findByDocumento(documento);

            model.addAttribute("perfil", cliente);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "perfil-cliente.html";
    }

    @PostMapping("/actualizar-perfil")
    public String registrar(ModelMap model, MultipartFile file, @RequestParam String documento,
            @RequestParam String nombres, @RequestParam String apellido,
            @RequestParam String contactoCel, @RequestParam String idCiudad) {

        Cliente cliente = null;

        try {
            cliente = clienteService.findByDocumento(documento);
            clienteService.modificarCliente(file, cliente);
            return "redirect:/";
        } catch (Exception e) {
            List<Ciudad> ciudades = ciudadService.listAll();
            model.put("ciudades", ciudades);
            model.put("error", e.getMessage());
            model.put("perfil", cliente);
            return "perfil-cliente.html";
        }
    }
}
