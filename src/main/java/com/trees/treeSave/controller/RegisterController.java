package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.UserService;
import com.trees.treeSave.services.UserVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/registro")
public class RegisterController {

    @Autowired
    private UserService us;

    @Autowired
    private UserVService usV;

//    @Autowired
//    private ClienteService cs;
    @GetMapping("")
    public String registro() {
        return "registro";
    }

    @PostMapping("")
    public String registroSave(Model model, @RequestParam String username,
            @RequestParam String password, @RequestParam String password2, @RequestParam(required=false) String documento, @RequestParam(required=false) String cuit) {

        try {
            if (documento != null) {
                us.save(username, password, password2, documento);
                return "redirect:/";
            } else if (cuit != null) {
                usV.save(username, password, password2, cuit);
                return "redirect:/";
            }
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            //model.addAttribute("cliente", cliente);
            model.addAttribute("username", username);
        }
        return "registro";
    }
}
