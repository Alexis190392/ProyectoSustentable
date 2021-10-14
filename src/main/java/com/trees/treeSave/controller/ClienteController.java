package com.trees.treeSave.controller;

import com.trees.treeSave.Entity.Ciudad;
import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Foto;
import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.services.CiudadService;
import com.trees.treeSave.services.ClienteService;
import com.trees.treeSave.services.ListaService;
import com.trees.treeSave.services.PLService;
import com.trees.treeSave.services.ProductoServicio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ClienteService clienteService;

    @Autowired
    private CiudadService ciudadService;
    
    @Autowired
    private ListaService ls;
    @Autowired
    private ProductoServicio ps;
    
    @Autowired
    private PLService pls;

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
            model.addAttribute("clientes", clienteService.listAllByQ(q));
        } else {
            model.addAttribute("clientes", clienteService.listAll());
        }
        return "cliente-list";
    }

    @GetMapping("/form")
    public String crearCliente(Model model, @RequestParam(required = false) String documento, @RequestParam(required = false) String action) {
        if (documento != null) {
            Optional<Cliente> optional = clienteService.findById(documento);
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
        clienteService.deleteById(id);
        return "redirect:/cliente/list";
    }

    @PostMapping("/save")
    public String guardarCliente(Model model, @RequestParam(required = false) MultipartFile archivo, RedirectAttributes redirectAttributes,
            @ModelAttribute Cliente cliente, @RequestParam String action) {
        try {
            if (action.equals("edit")) {
                
                redirectAttributes.addFlashAttribute("success", "Cliente modificado con éxito. Algunos cambios se verán cuando te loguees nuevamente.");
                clienteService.modificarCliente(archivo, cliente);
                 
                return "redirect:/usuario";
            } else {
                clienteService.validarCliente(cliente, archivo); //valida y guarda cliente en la bd
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

    //ESTE METODO HACE LEGIBLE EL DATE RECIBIDO EN LA VISTA CLIENTEFORM
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }
    
    @PostMapping("/createList")
    public String crearLista(Model model, 
                            @RequestParam String documento, 
                            @RequestParam String nombre, 
                            RedirectAttributes redat) throws WebException{
        try{
            //busco cliente
            Cliente c = clienteService.findByDocumento(documento);
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
            clienteService.save(c);
            redat.addFlashAttribute("sucess", "creado con exito");
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/cliente/panel";
        }
        return "redirect:/cliente/listado"; //url de la muestra de lista
    }
    
    
     @GetMapping("/listado")
    public String listadoYproductos(Model model, @RequestParam String documento) throws WebException{
           
        // listo mi lista
        model.addAttribute("miLista", pls.listAll(documento));
        //listo productos
        model.addAttribute("productos",ps.listAll());
        
        return "usuario-lista";
    }
    
    @GetMapping("/agregarProducto")
    public String agregarProductos(@RequestParam String documento, @RequestParam String sku, RedirectAttributes redat)  throws WebException{
        pls.agregar(documento, sku);
        redat.addFlashAttribute("documento", documento);
        
        return "redirect:/cliente/listado?documento="+documento;
    }
    
}
