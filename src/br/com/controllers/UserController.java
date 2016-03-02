package br.com.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.com.dao.UserDao;
import br.com.dao.UserDaoImpl;


@ManagedBean
@SessionScoped
public class UserController implements Serializable {  
      
	private Logger logger = Logger.getLogger(getClass());
    private br.com.models.User user;  
    private UserDao dao = new UserDaoImpl();
    
      
      
    public UserController(){  
    	user = new br.com.models.User();  
        SecurityContext context = SecurityContextHolder.getContext();  
        if(context instanceof SecurityContext){  
            Authentication authentication = context.getAuthentication();  
            if(authentication instanceof Authentication){
            	completSessionUser(((User)authentication.getPrincipal()).getUsername());
            }  
        }  
          
    }  
  
    public br.com.models.User getUser() {  
        return user;  
    }  
  
    public void setUser(br.com.models.User user) {  
        this.user = user;  
    }
    
	private void completSessionUser(String login) {
		try {
			List<br.com.models.User> tempUser = dao.searchByID(login);
			user.setLogin(tempUser.get(0).getLogin());
			user.setName(tempUser.get(0).getName());
			logger.info("Usuário logado com sucesso: " + user.getName());
		} catch (Exception ex) {
			logger.error("Erro ao registrar usuario logado", ex);
		}
	}
      
      
  
}  

