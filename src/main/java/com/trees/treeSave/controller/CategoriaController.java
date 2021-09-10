package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Categoria;
import com.trees.treeSave.services.CategoriaService;
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

/**
 *
 * @author Fede
 */
@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired(required = true)
    private CategoriaService categoriaService;
    
    @GetMapping("/list")
    public String listarCategorias(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("categorias", categoriaService.listAllByQ(q));
        } else {
            model.addAttribute("categorias", categoriaService.listAll());
        }
        return "categoria-list";
    }
    
     @GetMapping("/deleteTH")
    public String eliminarCategoria(@RequestParam(required = true) String id) {
        categoriaService.deleteById(id);
        return "redirect:/categoria/list";
    }
    
    @GetMapping("/form")
    public String crearCategoria(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Categoria> optional = categoriaService.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("categoria", optional.get());
            } else {
                return "categoria-list";
            }
        } else {
            model.addAttribute("categoria", new Categoria());
        }
        return "categoria-form";
    }
    
    @PostMapping("/save")
    public String guardarCategoria(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Categoria categoria) {
        try {
            categoriaService.save(categoria);
            redirectAttributes.addFlashAttribute("success", "Categoría guardada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/categoria/list";
    }
}
