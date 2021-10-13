package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Vendedor;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.UserService;
import com.trees.treeSave.services.VendedorServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService us;
    @Autowired
    private ClienteService cs;

    @Autowired
    private VendedorServicio vendedorServicio;

    @GetMapping("")
    public String login(Model model, @RequestParam(required = false) String error,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String logout,
            RedirectAttributes redat) {
        if (error != null) {
            model.addAttribute("error", "El usuario o la contraseña son incorrectos");
        }
        if (username != null) {
            model.addAttribute("username", username);
            try {
                redat.addFlashAttribute("vendedor", vendedorServicio.findByCuit(us.findVendedorByUsername(username).getCuit()));
                redat.addFlashAttribute("cliente", cs.findByDocumento(us.findClienteByUsername(username).getDocumento()));
            } catch (WebException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "redirect:/";
    }

}
