<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trees.treeSave.services;

import com.trees.treeSave.Entity.User;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ivan Doom Days
 */
@Service
public class UserService {

    @Autowired//para llamar al UserRepository
    private UserRepository userRepository; //creo obj de tipo UserRepo..

    public User save(String username, String password, String password2) throws WebException { //recibimos en save ,paso 2 contraseñas para compararlas
        User user = new User();
        if (username.isEmpty() || username == null) { // usuario ni vacio ni nulo
            throw new WebException("El usuario no puede estar vacio");// aderimos la clausula para usar WebException 
        }
        if (password.isEmpty() || password == null || password2.isEmpty() || password2.isEmpty()) { //password ni vacio ni nulo
            throw new WebException("El contraseña no puede estar vacio");// aderimos la clausula para usar WebException 
            }
         if (!password.equals(password2)) { //comparo las contraseñas , uso !password para para saber si NO son iguales.
            throw new WebException("Las contraseñas no son iguales");
            }
         user.setusername(username);
         user.setPassword(password);// se deja simple aun, para configurar despuescon Bcry

        return userRepository.save(user);   //tener cuidado cuando pongo el return, por hay lo ponemos en la llave de abajo.
    }

=======
package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.Users;
import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.enumeraciones.Role;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Date;
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
    private ClienteService clienteService;

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
>>>>>>> 777bc056c8ce2e372019f2f2510fea93eef74028
}
