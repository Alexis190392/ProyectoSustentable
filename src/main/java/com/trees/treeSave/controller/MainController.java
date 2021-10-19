package com.trees.treeSave.controller;

import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    
    @Autowired
    private ClienteService cs;
    
    @GetMapping("")
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/super")
    public String superSu(){
        return "panel-SuperSu";
    }
    
    @GetMapping("/usuario")
    public String usuario(Model model){
        model.addAttribute("clientes", cs.listAll());
        return "panel-Usuario";
    }
    
    @GetMapping("/vendedor")
    public String vendedor(){
        return "panel-Vendedor";
    }
}