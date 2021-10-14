package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.ListaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/lista")
public class ListaController {
    
    @Autowired
    private ListaService ls;
    
    @GetMapping("/list")
    public String listado(Model model, @RequestParam(required=false) String q){
        
        model.addAttribute("listas",ls.listAll());   
        return "pruebas-list";
    }
    
    
   @GetMapping("/form")
   public String crearLista(Model model, @RequestParam(required = false) String id){
       model.addAttribute("lista", new Lista());
       return "prueba-form";
   }
   
   @PostMapping("/save")
   public String nuevaLista(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Lista lista ){
       
        ls.crearLista(lista);
        return "redirect:/lista/list";
   }

}