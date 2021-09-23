package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.CategoriaService;
import com.trees.treeSave.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService ps;
    @Autowired
    private CategoriaService cs;
    
    
    
    @GetMapping("/list")
    public String listarProductos(Model model, @RequestParam(required = false) String sku) {
        if (sku != null) {
            model.addAttribute("productos", ps.listByQuery(sku));
        } else {
            model.addAttribute("productos", ps.listAll());
        }
        
        return "producto-list";
    }

    @GetMapping("/form")
    public String crearProducto(Model model,@RequestParam(required=false) String codigoBarra){
        if(codigoBarra != null){
            Producto p = ps.searchCod(codigoBarra);
            if(p != null){
                model.addAttribute("producto",p);
            } else {
                return "redirect:/producto/list";
            }
        }else{
            model.addAttribute("producto", new Producto());
        }
        model.addAttribute("tipos",ps.listTipo());
        model.addAttribute("categorias",cs.listAll());
        return "producto-form";
    }

    @PostMapping("/save")
    public String guardarProducto(RedirectAttributes redat, @ModelAttribute Producto p) {
        try {
            ps.save(p);
            redat.addFlashAttribute("success", "Producto guardado correctamente");
        } catch (WebException e) {
            redat.addFlashAttribute("error", e.getMessage()); //mandando el mensaje de error a donde es redireccionado
        }
        return "redirect:/producto/list";
    }

    //para eliminar
    @GetMapping("/delete")
    public String eliminarProducto(@RequestParam(required = true) String sku) {
        ps.deleteByCod(sku); //desde producto servicio permite la eliminacion
        return "redirect:/producto/list";
    }

}
