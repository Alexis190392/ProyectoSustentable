package com.trees.treeSave.RegistroCliente;

import com.trees.treeSave.excepciones.WebException;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository ur;


    @Transactional
    public Usuario save(Usuario u, String username, String password, String password2, Integer documento) throws WebException{
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        //valido que no haya otro con mismo dni
        if(ur.findById(documento+"").isPresent()){
            throw new WebException("El numero de documento ya se encuentra registrado.");
                    //derivar a recuperar contraseña
        }
        //valido que username no este vacio        
        if(username == null || username.isEmpty()){
            throw new WebException("El usuario no puede estar vacio");
        }
        //valido que no exista otro user igual        
        if(ur.findByUser(username)!= null && username!=null){
            throw new WebException("El nombre de usuario ya existe");
        }
        //valido el ingreso de ambas pass
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            throw new WebException("La contraseña no puede estar vacía");
        }
        //valido que ambas contraseñas sean iguales
        if (!password.equals(password2)) {
            throw new WebException("Las contraseñas deben ser iguales.");
        }
        //seteo todo en el uuario creado        
        u.setUsername(username);
        //encripto pass
        u.setPassword(encoder.encode(password));
        //seteo el documento
        u.setDocumento(documento+"");
        //seteo automaticamente fecha de alta
        u.setAlta(new Date());
                
        return ur.save(u);
    }
    
    
            
}
