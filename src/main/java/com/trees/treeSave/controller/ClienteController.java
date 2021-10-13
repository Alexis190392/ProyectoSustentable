package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Cliente;
<<<<<<< HEAD
import com.trees.treeSave.Entity.Foto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.CiudadService;
import com.trees.treeSave.services.ClienteService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
=======
import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.CiudadService;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.ListaService;
import com.trees.treeSave.services.PLService;
import com.trees.treeSave.services.ProductoServicio;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
>>>>>>> Develop-Alexis
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
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService cs;

    @Autowired
    private CiudadService ciudadService;

//    @GetMapping("/usuario")
//    public String usuario(Model model){
//       return "panel-Usuario";
//        return "crear-usuario";
//    }
        
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/panel")
    public String panelUsuario(){
        return "panel-Usuario";
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarClientes(Model model, @RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("clientes", cs.listAllByQ(q));
        } else {
            model.addAttribute("clientes", cs.listAll());
        }
        return "cliente-list";
    }

    @GetMapping("/form")
    public String crearCliente(Model model, @RequestParam(required = false) String documento, @RequestParam(required = false) String action) {
        if (documento != null) {
            Optional<Cliente> optional = cs.findById(documento);
            if (optional.isPresent()) {
                model.addAttribute("cliente", optional.get());
                model.addAttribute("action", action);
            } else {
                return "registro-cliente.html"; /// a modificar en la parte de perfil
            }
        } else {
            model.addAttribute("cliente", new Cliente());
            model.addAttribute("action", action);
        }
        model.addAttribute("ciudades", ciudadService.listAll());
        return "registro-cliente.html";
    }

    @GetMapping("/delete")
    public String eliminarCliente(@RequestParam(required = true) String id) {
        cs.deleteById(id);
        return "redirect:/cliente/list";
    }

    @PostMapping("/save")
    public String guardarCliente(Model model, @RequestParam(required = false) MultipartFile archivo, RedirectAttributes redirectAttributes,
            @ModelAttribute Cliente cliente, /*@ModelAttribute Ciudad ciudad,*/ @ModelAttribute Foto foto, @RequestParam String action, @RequestParam String nombres, @RequestParam String apellido,
            @RequestParam String contactoCel, @RequestParam String contactoMail, @RequestParam(required = false) Date fechaNacimiento,
            @RequestParam(required = true) String documento, @RequestParam(required = false) String idCiudad) {
        try {
            if (action.equals("edit")) {
<<<<<<< HEAD
                
=======
                cs.modificarCliente(archivo, cliente);
>>>>>>> Develop-Alexis
                redirectAttributes.addFlashAttribute("success", "Cliente modificado con éxito.");
                clienteService.modificarCliente(archivo, nombres, apellido, contactoCel, contactoMail, fechaNacimiento, documento, idCiudad);
                 
                return "redirect:/";
            } else {
                cs.validarCliente(cliente, archivo); //valida y guarda cliente en la bd
                redirectAttributes.addFlashAttribute("success", "Cliente guardado con éxito.");
            }
            redirectAttributes.addFlashAttribute("documento", cliente.getDocumento());
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("cliente", cliente);
            model.addAttribute("ciudades", ciudadService.listAll());
            model.addAttribute("foto", archivo);
            return "registro-cliente";
        }
        return "redirect:/registro";
    }

    
     /*
    Update 10/10/2021
    */
    
    
    @Autowired
    private ListaService ls;
    @Autowired
    private ProductoServicio ps;
    
    
    @PostMapping("/createList")
    public String crearLista(Model model, 
                            @RequestParam String documento, 
                            @RequestParam String nombre, 
                            RedirectAttributes redat) throws WebException{
        try{
            //busco cliente
            Cliente c = cs.findByDocumento(documento);
            //creo una lista
            Lista l = new Lista();
            //le seteo el nombre que se elije
            l.setNombreList(nombre);
            //la genero con id en bs
            ls.create(l);
            
            ls.cambiarNombre(l, nombre);
            //seteo en el cliente
 //           c.setLista(l);
            //guardo cliente
            cs.save(c);
            redat.addFlashAttribute("sucess", "creado con exito");
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/cliente/panel";
        }
        return "redirect:/cliente/listado"; //url de la muestra de lista
    }
    
//    @GetMapping ("/listado")
//    public String listadoProductos(Model model, 
//                                @RequestParam String documento) throws WebException{
//        try{
//            if(cs.findByDocumento(documento) != null){
//                //modelo lista creada para vista
//                if( cs.findByDocumento(documento).getLista() !=null){
////                    model.addAttribute("lista", ls.conversion(cs.findByDocumento(documento).getLista().getListado()));
//                } else {
//                    model.addAttribute("lista", new Lista());
//                }
//                
//                //modelo listado de productos
//                model.addAttribute("productos", ps.listAll());
//            }
//        } catch (WebException e){
//            model.addAttribute("error", e.getMessage());
//        }
//        return "usuario-lista";//url de la muestra de lista
//    }
//    
//    @PostMapping("/agregarProducto")
////    @GetMapping("/agregarProducto")
//    public String agregarProducto(Model model,
//                                @RequestParam String documento, 
//                                @ModelAttribute Producto producto, 
//                                @RequestParam Integer cantidad) throws WebException{
//       
// 
//       return "redirect:/cliente/listado";
//    }
    
    @Autowired
    private PLService pls;
    
    /* update 11/10/2021 */
    @GetMapping("/listado")
    public String listadoYproductos(Model model, @RequestParam String documento) throws WebException{
//        //llevo la lista en forma de list al front
//        try{
//            if(ls.conversion(ls.findById(cs.findByDocumento(documento).getLista()).get().getListado()) == null || 
//                    ls.conversion(ls.findById(cs.findByDocumento(documento).getLista()).get().getListado()).isEmpty()){
//                model.addAttribute("error", "No hay productos para mostrar");
//            } else{
//                try{
//                    model.addAttribute("lista", ls.conversion(ls.findById(cs.findByDocumento(documento).getLista()).get().getListado()));
//                } catch (WebException ex) {
//                     model.addAttribute("lista", new ArrayList());
//        }
//                 
//            }
//           
//        } catch(WebException e){
//            model.addAttribute("error", e.getMessage());
//        }


            
        // listo mi lista
        model.addAttribute("miLista", pls.listAll(documento));
        //listo productos
        model.addAttribute("productos",ps.listAll());
        
        return "usuario-lista";
    }
    
//    @PostMapping("/agregarProducto")
//    public String agregarProductos(Model model,
//                                @RequestParam String documento, 
//                                @ModelAttribute Producto producto, 
//                                @RequestParam Integer cantidad) throws WebException{
//        
//       ls.agregarProductos(ls.findById(cs.findByDocumento(documento).getLista()).get(), producto.getCodigoBarra());
//        
//        
//        
//        return "redirect:/cliente/listado";
//    }
    
    @GetMapping("/agregarProducto")
    public String agregarProductos(@RequestParam String documento, @RequestParam String sku, RedirectAttributes redat)  throws WebException{
        pls.agregar(documento, sku);
        redat.addFlashAttribute("documento", documento);
        
        return "redirect:/cliente/listado?documento="+documento;
    }
}
