package br.com.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="ga_autorization")
public class Autorizacao implements Serializable {
 
    @Id
    private String name;
 
    public Autorizacao() {
    }

	public String getName() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}
 
    
 
}
