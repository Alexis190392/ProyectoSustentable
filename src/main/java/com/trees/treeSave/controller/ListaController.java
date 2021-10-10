package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Cliente;
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
    @Autowired
    private ClienteService cs;
    
   /* @GetMapping("/list")
    public String listado(Model model, @RequestParam(required=false) String id){
        
        model.addAttribute("listas",ls.listAll());   
        return "pruebas-list";
    }
    
    
   @GetMapping("/form")
   public String crearLista(Model model, @RequestParam(required = false) String documento, RedirectAttributes redat){
       try{
           //taigo el documento de la session y busco al cliente
           Cliente c = cs.findByDocumento(documento);
           //si es distinto de null, que me traiga la lista, si no hay una, que me la cree
           if(c != null){
               if(c.getLista() == null){
                   model.addAttribute("lista", new Lista());
               } else {
                   model.addAttribute("lista", c.getLista());
               }
               model.addAttribute("cliente", c);
//               redat.addFlashAttribute("id",c.getLista().getId());
           } else{
               return "redirect:/usuario/panel";
           }
           
       } catch(WebException e){
           model.addAttribute("error", e.getMessage());
       }
       
       return "usuario-lista";
   }
   
   @PostMapping("/save")
   public String nuevaLista(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Lista lista, @ModelAttribute String documento) throws WebException{
        model.addAttribute("lista", lista);
        Lista l = ls.crearLista(lista);
        System.out.print(l.getId());
        System.out.print(l.getNombreList());
        
        //crea la lista y la seteo en el cliente
        Cliente c = cs.findByDocumento(documento);
        
        //c.setLista(l);
        cs.save(c);
        

        return "redirect:/usuario/panel";
   }

*/

}