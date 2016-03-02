package br.com.models;

import java.io.Serializable;
import java.util.Date;

public class Import implements Serializable {
	
	private Long remessa;
	private Long conta;
	private Long atendimento;
	private String paciente;
	private Date date;
	private String origem;
	private String he;
	private String atividade;
	private String grupo;
	private String medico;
	private String convenio;
	private Long codProc;
	private String procedimento;
	private String quantidade;
	private Double valor;
	private String repasse;
	
	public Long getRemessa() {
		return remessa;
	}
	public void setRemessa(Long remessa) {
		this.remessa = remessa;
	}
	public Long getConta() {
		return conta;
	}
	public void setConta(Long conta) {
		this.conta = conta;
	}
	public Long getAtendimento() {
		return atendimento;
	}
	public void setAtendimento(Long atendimento) {
		this.atendimento = atendimento;
	}
	public String getPaciente() {
		return paciente;
	}
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getHe() {
		return he;
	}
	public void setHe(String he) {
		this.he = he;
	}
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	public Long getCodProc() {
		return codProc;
	}
	public void setCodProc(Long codProc) {
		this.codProc = codProc;
	}
	public String getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getRepasse() {
		return repasse;
	}
	public void setRepasse(String repasse) {
		this.repasse = repasse;
	}
	
	

}
