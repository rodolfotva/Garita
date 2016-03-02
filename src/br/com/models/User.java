package br.com.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ga_user")
public class User implements Serializable{
	
	@Id
	@Column(name = "username")
	private String login;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	private String pass;
	
	@Column(name = "enable", columnDefinition = "BOOLEAN")
    private boolean enable;
	

	public User(){
	}
	
	public User(String nome, String login, String pass, boolean enable){
		this.login = login;
		this.name = nome;
		this.pass = pass;
		this.enable = enable;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final User other = (User) obj;
	        if (this.login != other.login) {
	            return false;
	        }
	        return true;
	    }
	
	
}
