package com.trees.treeSave.controller;

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
                return "redirect:/producto/list";
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
        return "redirect:/producto/list";
    }

    //para eliminar
    @GetMapping("/delete")
    public String eliminarProducto(@RequestParam(required = true) String sku) {
        ps.deleteByCod(sku); //desde producto servicio permite la eliminacion
        return "redirect:/producto/list";
    }

}
