package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Users;
import com.trees.treeSave.enumeraciones.Role;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
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

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private ClienteService clienteServicio;

    @Transactional
    public Users save(String username, String clave, String clave2, String documento) throws WebException {
        Users usuario = new Users();
        if (documento == null | documento.isEmpty()) {
            throw new WebException("El documento no puede estar vacio");
        }
        Cliente cliente = clienteServicio.findByDocumento(documento);
        if (cliente != null) {
            throw new WebException("El documento ya existe en la base de datos.");
        }
        if (username == null | username.isEmpty()) {
            throw new WebException("El username no puede estar vacio");
        }
        if (findByUsername(username) != null) {
            throw new WebException("El username que queres usar ya existe.");
        }
        if (clave == null | clave2 == null | clave.isEmpty() | clave2.isEmpty()) {
            throw new WebException("La contraseña no puede estar vacía");
        }
        if (!clave.equals(clave2)) {
            throw new WebException("Las contraseñas deben ser iguales.");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        usuario.setId(cliente.getId());
//        usuario.setDocumento(cliente.getDocumento());
//        usuario.setNombre(cliente.getNombre());
//        usuario.setApellido(cliente.getApellido());
//        usuario.setDomicilio(cliente.getDomicilio());
//        usuario.setTelefono(cliente.getTelefono());
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(clave));
        usuario.setRol(Role.USER);
        clienteServicio.delete(cliente);
        return usuarioRepository.save(usuario);
    }

    public Users findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
/*
    public List<Users> listAllByQ(String q) {
        return usuarioRepository.findAllByQ("%" + q + "%");
    }*/

    public List<Users> listAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users usuario = usuarioRepository.findByUsername(username);
            Users user;
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            return new User(username, usuario.getPassword(), authorities);
        } catch (Exception e) {
            throw new UnsupportedOperationException("El usuario no existe.");
        }
    }
}
