package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Vendedor;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.CiudadService;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.VendedorServicio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorServicio vendedorServicio;

    @Autowired
    private CiudadService ciudadService;

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarVendedores(Model model, @RequestParam(required = false) String q) throws WebException {
        if (q != null) {
            model.addAttribute("vendedores", vendedorServicio.listAllByQ(q));
        } else {
            model.addAttribute("vendedores", vendedorServicio.listAll());
        }
        return "vendedor-list";
    }

    @GetMapping("/form")
    public String crearVendedor(Model model, @RequestParam(required = false) String cuit,
            @RequestParam(required = false) String action) throws WebException {

        if (cuit != null) {
            Optional<Vendedor> optional = vendedorServicio.findById(cuit);
            if (optional.isPresent()) {
                model.addAttribute("vendedor", optional.get());
                model.addAttribute("action", action);
            } else {
                return "vendedor-list";
            }
        } else {
            model.addAttribute("vendedor", new Vendedor());
            model.addAttribute("action", action);
        }
        //estamos metiendo el th:each Ciudades como parametro para añadirlo junto con listAll
        model.addAttribute("ciudades", ciudadService.listAll());
        return "registro-vendedor";
    }

    @GetMapping("/delete")
    public String eliminarVendedor(@RequestParam(required = true) String id) throws WebException {
        vendedorServicio.deleteById(id);
        return "redirect:/vendedor/list";
    }

    @PostMapping("/save")
    public String guardarVendedor(Model model, @RequestParam(required = false) MultipartFile archivo, RedirectAttributes redirectAttributes,
            @ModelAttribute Vendedor vendedor, @RequestParam(required = false) String action,
            @RequestParam(required = true) String cuit, @RequestParam String nombre, @RequestParam String domicilio,
            /*@ModelAttribute Ciudad ciudad,*/ @RequestParam(required = false) String idCiudad,
            @RequestParam String contactoMail, @RequestParam String contactoCel) throws WebException {
        
        try {

            if (action.equals("edit")) {
                vendedorServicio.modificarVendedor(cuit, nombre, domicilio, idCiudad, contactoMail, contactoCel);
                redirectAttributes.addFlashAttribute("success", "Vendedor modificado con éxito.");
            } else {
                vendedorServicio.validarVendedor(vendedor, archivo);
                redirectAttributes.addFlashAttribute("success", "Comercio guardado con éxito.");
            }
            redirectAttributes.addFlashAttribute("cuit", vendedor.getCuit());
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("vendedor", vendedor);
            model.addAttribute("ciudades", ciudadService.listAll());
            //model.addAttribute("foto", archivo);
            return "registro-vendedor";
        }
        return "redirect:/registro";
    }

    //ESTE METODO  HACE LEGIBLE EL DATE RECIBIDO EN LA VISTA CLIENTEFORM
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }
}
