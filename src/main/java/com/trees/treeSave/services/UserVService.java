package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Cliente;
import com.trees.treeSave.Entity.UserV;
import com.trees.treeSave.Entity.Vendedor;
import com.trees.treeSave.enumeraciones.Nivel;
import com.trees.treeSave.enumeraciones.Role;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.UserRepository;
import com.trees.treeSave.repositories.UserVRepository;
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
public class UserVService implements UserDetailsService {

    @Autowired
    private UserVRepository usuarioVRepository;

    @Autowired
    private VendedorServicio vendedorServicio;

    @Transactional
    public UserV save(String username, String password, String password2, String cuit) throws WebException {
        UserV usuario = new UserV();
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

        usuario.setCuit(v.getCuit());
        usuario.setNombre(v.getNombre());
        usuario.setDomicilio(v.getDomicilio());
        usuario.setCiudad(v.getCiudad());
        usuario.setContactoMail(v.getContactoMail());
        usuario.setContactoCel(v.getContactoCel());
        usuario.setNivel(Nivel.SEMILLA);
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));

        usuario.setRol(Role.VENDEDOR);
        vendedorServicio.delete(v);
        return usuarioVRepository.save(usuario);
    }

    public UserV findByUsername(String username) {
        return usuarioVRepository.findByUsernameOrMail(username);
    }

//    public List<Users> listAllByQ(String q) {
//        return usuarioRepository.findAllByQ("%" + q + "%");
//    }
    public List<UserV> listAll() {
        return usuarioVRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserV usuario = usuarioVRepository.findByUsernameOrMail(username);
            UserV userV;
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
}
