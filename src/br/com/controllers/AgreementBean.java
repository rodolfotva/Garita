package br.com.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.dao.AgreementDao;
import br.com.dao.AgreementDaoImpl;
import br.com.models.Agreement;

@ManagedBean
@SessionScoped
public class AgreementBean implements Serializable {

	private Logger logger = Logger.getLogger(getClass());
	private Agreement agreement = new Agreement();
	private List<Agreement> agreements = new ArrayList<Agreement>();
	private AgreementDao dao = new AgreementDaoImpl();
	private Agreement selectAgreement = new Agreement();
	private int totalAgreements;

	public AgreementBean() {
		agreements = dao.consultar();
		setTotalAgreements(agreements.size());
	}

	public void save() {
		try {
			dao.save(agreement);
			agreement = new Agreement();
			agreements = dao.consultar();
			setTotalAgreements(agreements.size());
			geraMsg("Registro Salvo com Sucesso", null);
		} catch (Exception ex) {
			geraMsg("Erro ao salvar registro", ex);
		}
	}

	public void edit() {
		try {
			dao.save(selectAgreement);
			agreements = dao.consultar();
			setTotalAgreements(agreements.size());
			geraMsg("Registro Salvo com Sucesso", null);
		} catch (Exception ex) {
			geraMsg("Erro ao salvar registro", ex);
		}
	}

	public void delete() {
		try {
			dao.delete(agreement);
			agreements = dao.consultar();
			setTotalAgreements(agreements.size());
			geraMsg("Registro excluido com Sucesso", null);
		} catch (Exception ex) {
			geraMsg("Erro ao excluir registro", ex);
		}

	}

	private void geraMsg(String mensagem, Exception ex) {
		if (ex != null) {
			logger.error(mensagem, ex);
		} else {
			logger.info(mensagem);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", mensagem));
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public List getAgreements() {
		return agreements;
	}

	public void setAgreements(List agreements) {
		this.agreements = agreements;
	}

	public Agreement getSelectAgreement() {
		return selectAgreement;
	}

	public void setSelectAgreement(Agreement selectAgreement) {
		this.selectAgreement = selectAgreement;
	}

	public int getTotalAgreements() {
		return totalAgreements;
	}

	public void setTotalAgreements(int totalAgreements) {
		this.totalAgreements = totalAgreements;
	}

}
