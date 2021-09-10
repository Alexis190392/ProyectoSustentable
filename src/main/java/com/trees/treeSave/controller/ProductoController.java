package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.enumeraciones.Tipo;
import com.trees.treeSave.services.CategoriaService;
import com.trees.treeSave.services.ProductoServicio;
import static java.util.Collections.list;
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
    private ProductoServicio ps;
    @Autowired
    private CategoriaService cs;
    
    
    
    @GetMapping("/list")
    public String listarProductos(Model model, @RequestParam(required = false) String query) {
        if (query != null) {
            model.addAttribute("productos", ps.listByQuery(query));
        } else {
            model.addAttribute("productos", ps.listAll());
        }
        model.addAttribute("tipos",ps.listTipo());
        model.addAttribute("categorias",cs.listAll());
        return "administrarProductos";
    }

    @GetMapping("/form")
    public String crearEditorial(Model model,@RequestParam(required=false) String codigoBarra){
        if(codigoBarra != null){
            Optional<Producto> op = ps.searchCod(codigoBarra);
            if(op.isPresent()){
                model.addAttribute("producto", op.get());
            } else {
                return "redirect:/producto/list";
            }
        }else{
            model.addAttribute("producto", new Producto());
        }
        return "crearProducto";
    }

    @PostMapping("/save")
    public String guardarProducto(RedirectAttributes redat, @ModelAttribute Producto p) {
        try {
            ps.save(p);
            redat.addFlashAttribute("success", "Producto guardado correctamente");
        } catch (Exception e) {
            redat.addFlashAttribute("error", e.getMessage()); //mandando el mensaje de error a donde es redireccionado
        }
        return "redirect:/producto/list";
    }

    //para eliminar
    @GetMapping("/delete")
    public String eliminarProducto(@RequestParam(required = true) String codigo) {
        ps.deleteByCod(codigo); //desde producto servicio permite la eliminacion
        return "redirect:/producto/list";
    }

}
