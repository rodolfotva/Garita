package br.com.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ga_client")
public class Client implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id_cli")
	private Long id;
	
	@Column(name = "id_atend")
	private Long id_atend;
	
	@Column(name = "cldate")
	private Date cldate;
	
	@Column(name = "pacient")
	private String pacient;
	
	@Column(name = "origem")
	private String origin;
	
	@Column(name = "telephone")
	private String telephone;
	
	@Column(name = "matsame")
	private Long matsame;
	
	@Column(name = "service")
	private String service;
	
	@Column(name = "pgvalue")
	private double pgvalue;
	
	@Column(name = "pg")
	private int pg;
	
	@Column(name = "repasse")
	private String repasse;
	
	@OneToOne    
	@JoinColumn(name ="agreement", referencedColumnName = "id") 
	private Agreement agreement;
	
	@OneToOne    
	@JoinColumn(name ="newprocedure", referencedColumnName = "codigo") 
	private Procedure procedure;
	
	public Client() {
	}

	public Client(Long id, Long id_atend, Date cldate, String pacient, String origin, String telephone, Long matsame, String service, Agreement agreement, double pgvalue,  int pg, Procedure procedure, String repasse) {
		this.id = id;
		this.id_atend = id_atend;
		this.cldate = cldate;
		this.pacient = pacient;
		this.origin = origin;
		this.telephone = telephone;
		this.matsame = matsame;
		this.service = service;
		this.agreement = agreement;
		this.pg = pg;
		this.pgvalue = pgvalue;
		this.procedure = procedure;
		this.repasse = repasse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCldate() {
		return cldate;
	}

	public void setCldate(Date cldate) {
		this.cldate = cldate;
	}

	public String getPacient() {
		return pacient;
	}

	public void setPacient(String pacient) {
		this.pacient = pacient;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getMatsame() {
		return matsame;
	}

	public void setMatsame(Long matsame) {
		this.matsame = matsame;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		this.pg = pg;
	}

	public long getId_atend() {
		return id_atend;
	}

	public void setId_atend(Long id_atend) {
		this.id_atend = id_atend;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public double getPgvalue() {
		return pgvalue;
	}

	public void setPgvalue(double pgvalue) {
		this.pgvalue = pgvalue;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public String getRepasse() {
		return repasse;
	}

	public void setRepasse(String repasse) {
		this.repasse = repasse;
	}
	
	
}
