package br.com.dao;

import java.util.List;

import br.com.models.User;

public interface UserDao  extends DaoGenerico <User, Long>{
	
	public List consultar();
	public List searchByID(String login);

}