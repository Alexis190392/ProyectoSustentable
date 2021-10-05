<<<<<<< HEAD
/*
aca se hace la configuracion de seguridad
trabajamos con:
autenticacion (de los usuarios):que halla un usuario q 
se puede logear, una seccion.
autorizaciones:lo que tiene permitido cada usuario(rol)
 */

package com.trees.treeSave;
/**
 UserDatailService  (prueba un metodo) -> LoadByUserName 
 * (metodo q sobreescribimos para que un suario se logee)
 este metodo lo aplicamos en UserService.  (UserDatailService sera UserService)
 * -un metodo para configurar la autenticacion(le pasamos el UserService y la encriptacion que vamos a usar)
 * -configuracion de las peticiones http (para confg un login nuestro)
 * 
 */
public class Security {

}
=======
package com.trees.treeSave;


import com.trees.treeSave.services.UserService;
import static javafx.scene.input.KeyCode.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{
    
    //Autenticacion y las autorizaciones
    
    
    //UsersDetailsService -> loadByUserName -> UsuarioServicio
    @Autowired 
    private UserService us;
    
    
    //Un metodo que va a configurar la autenticacion
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(us).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    //la configuracion las peticiones http
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/*","/img/*","/js/*").permitAll()
                .and().formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/logincheck")
                .failureUrl("/login?error=error")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
        
    }
}
>>>>>>> 777bc056c8ce2e372019f2f2510fea93eef74028
