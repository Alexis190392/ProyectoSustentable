package com.trees.treeSave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    
    @GetMapping("")
    public String index(){
        return "index";
    }
    
    @GetMapping("/super")
    public String superSu(){
        return "panel-SuperSu";
    }
    
    @GetMapping("/usuario")
    public String usuario(){
        return "panel-Usuario";
    }
    
    @GetMapping("/vendedor")
    public String vendedor(){
        return "panel-Vendedor";
    }
}
