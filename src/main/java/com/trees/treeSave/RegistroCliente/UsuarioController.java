package com.trees.treeSave.RegistroCliente;

import com.trees.treeSave.excepciones.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registrarse")
public class UsuarioController {
    
    @Autowired
    private UsuarioService us;
    
    @GetMapping("")
    public String regitro(Model model){
        
        
       model.addAttribute("usuario",new Usuario());
        return("registro-usuario");
    }
    
    @PostMapping("")
    public String registrar(Model model, 
                            @RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String password2,
                            @RequestParam Integer documento,
                            @ModelAttribute Usuario usuario,
                            RedirectAttributes redat){
        try{
            us.save(usuario, username, password, password2, documento);
            redat.addFlashAttribute("success", "Usuario creado con exito");
            return "registro-usuario";///redirect al panel de iniciod e creacion de listas
        }catch (WebException e){
            redat.addFlashAttribute("error", e.getMessage());
        }
        
        model.addAttribute("usuario",usuario);
        model.addAttribute("username",username);
        model.addAttribute("password", password);
        model.addAttribute("password2", password2);
        model.addAttribute("documento",documento);
        
        return "registro-usuario";
    }
    
    
}
