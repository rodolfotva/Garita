package br.com.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ga_user_autorization")
public class UserAutorization {

	@Id
	@Column(name = "user_login")
	private String user;
	
	@Column(name = "autorization_name")
	private String autorization;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAutorization() {
		return autorization;
	}

	public void setAutorization(String autorization) {
		this.autorization = autorization;
	}
	
	
}
