package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Foto;
import com.trees.treeSave.Entity.UserCliente;
import com.trees.treeSave.Entity.UserVendedor;
import com.trees.treeSave.Entity.Vendedor;
import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.enumeraciones.Role;
import com.trees.treeSave.excepciones.WebException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import com.trees.treeSave.repositories.UserCRepository;
import com.trees.treeSave.repositories.UserVRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserCRepository usuarioRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private UserVRepository usuarioVRepository;

    @Autowired
    private VendedorServicio vendedorServicio;

    @Transactional
    public UserCliente saveCliente(String username, String password, String password2, String documento) throws WebException {
        UserCliente usuario = new UserCliente();
        Cliente c = clienteService.findByDocumento(documento);

        if (c == null) {
            throw new WebException("El documento no existe en la base de datos.");
        }

        if (documento == null || documento.isEmpty()) {
            throw new WebException("El documento no puede estar vacio");
        }

        if (username == null || username.isEmpty()) {
            throw new WebException("El username no puede estar vacio");
        }
//        if (findByUsername(username) != null) {
//            throw new WebException("El username que queres usar ya existe.");
//        }
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            throw new WebException("La contraseña no puede estar vacía");
        }
        if (!password.equals(password2)) {
            throw new WebException("Las contraseñas deben ser iguales.");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        usuario.setDocumento(c.getDocumento());
        usuario.setNombres(c.getNombres());
        usuario.setApellido(c.getApellido());
        usuario.setContactoCel(c.getContactoCel());
        usuario.setCiudad(c.getCiudad());
        usuario.setContactoMail(c.getContactoMail());
        usuario.setFechaNacimiento(c.getFechaNacimiento());
        usuario.setPuntajeAcumulado(0);
        usuario.setNivel(Nivel.SEMILLA);
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));
        usuario.setAlta(c.getAlta());
        usuario.setBaja(c.getBaja());
        usuario.setFoto(c.getFoto());
        usuario.setRol(Role.CLIENTE);
        clienteService.delete(c);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public UserVendedor saveVendedor(String username, String password, String password2, String cuit) throws WebException {
        UserVendedor usuario = new UserVendedor();
        Vendedor v = vendedorServicio.findByCuit(cuit);

        if (v == null) {
            throw new WebException("El cuit no existe en la base de datos.");
        }

        if (cuit == null || cuit.isEmpty()) {
            throw new WebException("El cuit no puede estar vacio");
        }

        if (username == null || username.isEmpty()) {
            throw new WebException("El username no puede estar vacio");
        }
        if (findVendedorByUsername(username) != null) {
            throw new WebException("El username que queres usar ya existe.");
        }
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            throw new WebException("La contraseña no puede estar vacía");
        }
        if (!password.equals(password2)) {
            throw new WebException("Las contraseñas deben ser iguales.");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

      
        usuario.setCuit(v.getCuit());
        usuario.setNombre(v.getNombre());
        usuario.setDomicilio(v.getDomicilio());
        usuario.setCiudad(v.getCiudad());
        usuario.setContactoMail(v.getContactoMail());
        usuario.setContactoCel(v.getContactoCel());
        usuario.setBaja(v.getBaja());
        usuario.setBaja(v.getBaja());
        usuario.setNivel(Nivel.SEMILLA);
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));

        usuario.setFoto(v.getFoto());
        usuario.setRol(Role.VENDEDOR);
        vendedorServicio.delete(v);
        return usuarioVRepository.save(usuario);
    }

    public UserCliente findClienteByUsername(String username) {
        return usuarioRepository.findByUsernameOrMail(username);
    }

    public UserVendedor findVendedorByUsername(String username) {
        return usuarioVRepository.findByUsernameOrMail(username);
    }

//    public List<Users> listAllByQ(String q) {
//        return usuarioRepository.findAllByQ("%" + q + "%");
//    }
    public List<UserCliente> listAllCliente() {
        return usuarioRepository.findAll();
    }

    public List<UserVendedor> listAllVendedor() {
        return usuarioVRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserCliente usuarioC = usuarioRepository.findByUsernameOrMail(username);

            if (usuarioC != null) {
                User user;
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + usuarioC.getRol()));

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes();

                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("usuariosession", usuarioC);
                return new User(username, usuarioC.getPassword(), authorities);
            } else {
                UserVendedor usuarioV = usuarioVRepository.findByUsernameOrMail(username);
                User user;
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + usuarioV.getRol()));

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes();

                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("usuariosession", usuarioV);

                return new User(username, usuarioV.getPassword(), authorities);
            }

        } catch (Exception e) {
            throw new UnsupportedOperationException("El usuario no existe.");
        }
    }

    public Optional<UserCliente> findCienteById(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<UserVendedor> findVendedorById(String id) {
        return usuarioVRepository.findById(id);
    }

//    @Transactional
//    public void modificarUsuarioCliente(MultipartFile file, UserCliente usuario) throws WebException {
//        if (usuario.getDocumento() == null) {
//            throw new WebException("El documento no puede ser nulo.");
//        }
//        if (usuario.getNombres() == null | usuario.getNombres().isEmpty()) {
//            throw new WebException("El nombre no puede ser nulo.");
//        }
//        if (usuario.getApellido() == null | usuario.getApellido().isEmpty()) {
//            throw new WebException("El apellido no puede ser nulo.");
//        }
//        if (usuario.getContactoCel() == null | usuario.getContactoCel().isEmpty()) {
//            throw new WebException("El telefono no puede ser nulo.");
//        }
//        usuario.setNombres(usuario.getNombres());
//        usuario.setApellido(usuario.getApellido());
//        usuario.setContactoCel(usuario.getContactoCel());
//        usuario.setDocumento(usuario.getDocumento());
//
//        String idFoto = null;
//
//        if (usuario.getFoto() != null) {
//            idFoto = usuario.getFoto().getId();
//        }
//        Foto foto = fotoService.actualizar(idFoto, file);
//        usuario.setFoto(foto);
//        usuarioRepository.save(usuario);
//
//    }
}
