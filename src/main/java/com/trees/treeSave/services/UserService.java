package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Foto;
import com.trees.treeSave.Entity.Lista;
import com.trees.treeSave.Entity.Users;
import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.enumeraciones.Role;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.UserRepository;
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

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository usuarioRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private FotoService fotoService;
    
    @Autowired
    private ListaService ls;
    
    @Transactional
    public Users save(String username, String password, String password2, String documento) throws WebException {
        Users usuario = new Users();
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
        usuario.setFoto(c.getFoto());
        Lista l = new Lista();
        l = ls.create(l);
        usuario.setLista(l.getId());
        usuario.setRol(Role.USER);
        clienteService.delete(c);
        return usuarioRepository.save(usuario);
    }
    
    public Users findByUsername(String username) {
        return usuarioRepository.findByUsernameOrMail(username);
    }

//    public List<Users> listAllByQ(String q) {
//        return usuarioRepository.findAllByQ("%" + q + "%");
//    }
    public List<Users> listAll() {
        return usuarioRepository.findAll();
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users usuario = usuarioRepository.findByUsernameOrMail(username);
            User user;
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            return new User(username, usuario.getPassword(), authorities);
        } catch (Exception e) {
            throw new UnsupportedOperationException("El usuario no existe.");
        }
    }
    
    public Optional<Users> findById(String id) {
        return usuarioRepository.findById(id);
    }
    
    @Transactional
    public void modificarUsuario(MultipartFile file, Users usuario) throws WebException {
        if (usuario.getDocumento() == null) {
            throw new WebException("El documento no puede ser nulo.");
        }
        if (usuario.getNombres() == null | usuario.getNombres().isEmpty()) {
            throw new WebException("El nombre no puede ser nulo.");
        }
        if (usuario.getApellido() == null | usuario.getApellido().isEmpty()) {
            throw new WebException("El apellido no puede ser nulo.");
        }
        if (usuario.getContactoCel() == null | usuario.getContactoCel().isEmpty()) {
            throw new WebException("El telefono no puede ser nulo.");
        }
        usuario.setNombres(usuario.getNombres());
        usuario.setApellido(usuario.getApellido());
        usuario.setContactoCel(usuario.getContactoCel());
        usuario.setDocumento(usuario.getDocumento());
        
        String idFoto = null;
        
        if (usuario.getFoto() != null) {
            idFoto = usuario.getFoto().getId();
        }
        Foto foto = fotoService.actualizar(idFoto, file);
        usuario.setFoto(foto);
        usuarioRepository.save(usuario);
        
    }
}
