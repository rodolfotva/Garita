package br.com.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.dao.UserAutorizationDao;
import br.com.dao.UserAutorizationDaoImpl;
import br.com.dao.UserDao;
import br.com.dao.UserDaoImpl;
import br.com.models.User;
import br.com.models.UserAutorization;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

	private Logger logger = Logger.getLogger(getClass());
	private User user = new User();
	private List<User> users = new ArrayList<User>();
	private UserDao dao = new UserDaoImpl();
	private int totalUsers;
	private User selectUser = new User();
	
	public UserBean() {
		users = dao.consultar();
		setTotalUsers(users.size());
	}

	public void save() {
		try {
			user.setEnable(true);
			dao.save(user);
			UserAutorization userAuto = new UserAutorization();
			UserAutorizationDao userAutoDao = new UserAutorizationDaoImpl();
			userAuto.setAutorization("ROLE_USER");
			userAuto.setUser(user.getLogin());
			userAutoDao.save(userAuto);
			user = new User();
			users = dao.consultar();
			setTotalUsers(users.size());
			geraMsg("Registro Salvo com Sucesso", false);
		} catch (Exception ex) {
			geraMsg("Erro ao salvar registro", true);
		}
	}

	public void edit() {
		try {
			selectUser.setEnable(true);
			dao.save(selectUser);
			users = dao.consultar();
			geraMsg("Registro Salvo com Sucesso", false);
		} catch (Exception ex) {
			geraMsg("Erro ao salvar registro", true);
		}
	}

	public void delete() {
		try {
			UserAutorization userAuto = new UserAutorization();
			UserAutorizationDao userAutoDao = new UserAutorizationDaoImpl();
			userAuto.setAutorization("ROLE_USER");
			userAuto.setUser(user.getLogin());
			userAutoDao.delete(userAuto);
			dao.delete(user);
			users = dao.consultar();
			setTotalUsers(users.size());
			geraMsg("Registro Excluido com Sucesso", false);
		} catch (Exception ex) {
			geraMsg("Erro ao excluir registro", true);
		}
	}

	private void geraMsg(String mensagem, boolean erro) {
		if (erro) {
			logger.error(mensagem);
		} else {
			logger.info(mensagem);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", mensagem));
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List getUsers() {
		return users;
	}

	public void setUsers(List users) {
		this.users = users;
	}

	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public User getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(User selectUser) {
		this.selectUser = selectUser;
	}
}
