package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Categoria;
import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.CategoriaService;
import com.trees.treeSave.services.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoServicio ps;
    @Autowired
    private CategoriaService cs;

    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping("")
    public String PanelProductos(Model model, @RequestParam(required = false) String sku, @RequestParam(required = false) String q) {
        listarProductos(model, sku);
        crearProducto(model, sku);
        listarCategorias(model, q);
        crearCategoria(model, q);
        return "panel-producto";
    }

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
    public String crearProducto(Model model, @RequestParam(required = false) String codigoBarra) {
        if (codigoBarra != null) {
            Producto p = ps.searchCod(codigoBarra);
            if (p != null) {
                model.addAttribute("producto", p);
            } else {
                return "redirect:/producto";
            }
        } else {
            model.addAttribute("producto", new Producto());
        }
        model.addAttribute("tipos", ps.listTipo());
        model.addAttribute("categorias", cs.listAll());

        return "producto-form";
    }

    

    @PostMapping("/save")
    public String guardarProducto(Model model, @RequestParam(required = true) String action
            , @RequestParam(required = false) MultipartFile archivo, RedirectAttributes redat
            , @ModelAttribute Producto p) {
        try {
            if (action.equals("edit")) {
                ps.modificarProducto(archivo, p);
                redat.addFlashAttribute("success", "Cliente modificado con éxito.");
            } else {
                ps.save(archivo, p);
                redat.addFlashAttribute("success", "Producto guardado correctamente");
            }

        } catch (WebException e) {
            redat.addFlashAttribute("error", e.getMessage()); //mandando el mensaje de error a donde es redireccionado
             model.addAttribute("producto", p);
        }
        return "redirect:/producto";
    }

    //para eliminar
    @GetMapping("/delete")
    public String eliminarProducto(@RequestParam(required = true) String sku) {
        ps.deleteByCod(sku); //desde producto servicio permite la eliminacion
        return "redirect:/producto";
    }
    
    
    /*CATEGORIAS*/



    
    @GetMapping("/listCat")
    public String listarCategorias(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("categorias", categoriaService.listAllByQ(q));
        } else {
            model.addAttribute("categorias", categoriaService.listAll());
        }
        return "panel-producto";
    }
    
     @GetMapping("/deleteCat")
    public String eliminarCategoria(@RequestParam(required = true) String id) {
        categoriaService.deleteById(id);
        return "redirect:/producto";
    }
    
    @GetMapping("/formCat")
    public String crearCategoria(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Categoria> optional = categoriaService.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("categoria", optional.get());
            } else {
                return "redirect:/producto";
            }
        } else {
            model.addAttribute("categoria", new Categoria());
        }
        return "panel-producto";
    }
    
    @PostMapping("/saveCat")
    public String guardarCategoria(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Categoria categoria) {
        try {
            categoriaService.save(categoria);
            redirectAttributes.addFlashAttribute("success", "Categoría guardada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/producto";
    }

}
