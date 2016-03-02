package br.com.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.dao.ProcedureDao;
import br.com.dao.ProcedureDaoImpl;
import br.com.models.Procedure;

@ManagedBean
@SessionScoped
public class ProcedureBean implements Serializable {

	private Logger logger = Logger.getLogger(getClass());
	private Procedure procedure = new Procedure();
	private List<Procedure> procedures = new ArrayList<Procedure>();
	private ProcedureDao dao = new ProcedureDaoImpl();
	private Procedure selectProcedure = new Procedure();
	private int totalProcedures;

	public ProcedureBean() {
		procedures = dao.consultar();
		setTotalProcedures(procedures.size());
	}

	public void save() {
		try {
			dao.save(procedure);
			procedure = new Procedure();
			procedures = dao.consultar();
			setTotalProcedures(procedures.size());
			geraMsg("Registro Salvo com Sucesso", null);
		} catch (Exception ex) {
			geraMsg("Erro ao salvar registro", ex);
		}
	}

	public void edit() {
		try {
			dao.save(selectProcedure);
			procedures = dao.consultar();
			setTotalProcedures(procedures.size());
			geraMsg("Registro Salvo com Sucesso", null);
		} catch (Exception ex) {
			geraMsg("Erro ao salvar registro", ex);
		}
	}

	public void delete() {
		try {
			dao.delete(procedure);
			procedures = dao.consultar();
			setTotalProcedures(procedures.size());
			geraMsg("Registro Excluido com Sucesso", null);
		} catch (Exception ex) {
			geraMsg("Erro ao excluir registro", ex);
		}
	}

	private void geraMsg(String mensagem, Exception ex) {
		if (ex!=null) {
			logger.error(mensagem, ex);
		} else {
			logger.info(mensagem);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", mensagem));
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public List getProcedures() {
		return procedures;
	}

	public void setProcedures(List procedures) {
		this.procedures = procedures;
	}

	public Procedure getSelectProcedure() {
		return selectProcedure;
	}

	public void setSelectProcedure(Procedure selectProcedure) {
		this.selectProcedure = selectProcedure;
	}

	public int getTotalProcedures() {
		return totalProcedures;
	}

	public void setTotalProcedures(int totalProcedures) {
		this.totalProcedures = totalProcedures;
	}

}
