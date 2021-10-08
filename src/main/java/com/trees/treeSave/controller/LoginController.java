package com.trees.treeSave.controller;

import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.UserService;
import com.trees.treeSave.services.UserVService;
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
    private UserVService usV;
    
    @Autowired
    private ClienteService cs;
    @Autowired
    private VendedorServicio vs;
    
 
    
    

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
                 if(us.findByUsername(username) != null){
                     redat.addFlashAttribute("cliente", cs.findByDocumento(us.findByUsername(username).getDocumento()));
                    // return "redirect:/cliente/panel-usuario";
                 }else if (usV.findByUsername(username) != null){
                     redat.addFlashAttribute("vendedor", vs.findByCuit(usV.findByUsername(username).getCuit()));
                     // return "redirect:/vendedor/panel-vendedor";
                 }
                
                
            } catch (WebException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "index.html";
    }

}