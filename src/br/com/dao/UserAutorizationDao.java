package br.com.dao;

import java.util.List;

import br.com.models.User;
import br.com.models.UserAutorization;

public interface UserAutorizationDao  extends DaoGenerico <UserAutorization, Long>{
	
	public List consultar();
	public List searchByID(String login);

}