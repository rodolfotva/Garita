package br.com.controllers;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;

import br.com.dao.AgreementDao;
import br.com.dao.AgreementDaoImpl;
import br.com.dao.ClientDao;
import br.com.dao.ClientDaoImpl;
import br.com.dao.ProcedureDao;
import br.com.dao.ProcedureDaoImpl;
import br.com.models.Agreement;
import br.com.models.Client;
import br.com.models.Import;
import br.com.models.Procedure;
import br.com.utils.HibernateUtility;

@ManagedBean
@SessionScoped
public class UploadBean implements Serializable {

	private Logger logger = Logger.getLogger(getClass());
	DateFormat dfOne = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat dfTwo = new SimpleDateFormat("dd/MM/yyyy");
	Procedure procedure = new Procedure();
	Agreement agreement = new Agreement();

	public void fileUpload(FileUploadEvent event) {
		try {
			logger.info("################################## Iniciando importação #################################");
			int nLines = 0;
			Import importDatas = new Import();
			List lstImportDatas = new ArrayList<Import>();
			String repasse = event.getFile().getFileName().substring(0, 6);
			logger.info("Lendo Arquivo:" + event.getFile().getFileName());
			Scanner scanner = new Scanner(event.getFile().getInputstream());

			while (scanner.hasNext()) {
				importDatas = new Import();
				String[] line;
				boolean notJump = true;
				String readLine = scanner.nextLine();
				line = readLine.split(";");
				if (line[0].startsWith("Total")) {
					nLines--;
					notJump = false;
				}
				if (notJump) {
					if (nLines != 0) {
						importDatas.setRemessa(Long.parseLong(line[0]));
						importDatas.setConta(Long.parseLong(line[1]));
						importDatas.setAtendimento(Long.parseLong(line[2]));
						importDatas.setPaciente(line[3]);
						importDatas.setDate(dfOne.parse(dfOne.format(dfTwo.parse(line[4]))));
						importDatas.setOrigem(line[5]);
						importDatas.setHe(line[6]);
						importDatas.setAtividade(line[7]);
						importDatas.setGrupo(line[8]);
						importDatas.setMedico(line[9]);
						importDatas.setConvenio(line[10]);
						importDatas.setCodProc(Long.parseLong(line[11]));
						importDatas.setProcedimento(line[12]);
						importDatas.setQuantidade(line[13]);
						importDatas.setValor(Double.parseDouble(line[14].replace(",", ".")));
						importDatas.setRepasse(repasse);

						lstImportDatas.add(importDatas);

					}
				}
				nLines++;
			}

			nLines--;
			logger.info("Numeros de linhas no arquivo: " + lstImportDatas.size());
			insertData(lstImportDatas);
			logger.info("################################## Fim importação #################################");
			logger.info(" ");
			geraMsg("Registro Salvo com Sucesso: " + event.getFile().getFileName(), null);
		} catch (Exception ex) {
			geraMsg("Erro ao salvar registro:", ex);
		}
	}

	private void insertData(List<Import> lstImportDatas) throws Exception {
		int regInsert = 1;
		logger.info("Iniciando inserção dos dados");
		ClientDao dao = new ClientDaoImpl();
		for (Import value : lstImportDatas) {
			logger.info("Registro: " + value.getAtendimento() + ";" + value.getPaciente() + ";" + value.getCodProc());
			Client client = new Client();

			checkProcedure(value.getCodProc(), value.getProcedimento());
			checkAgreement(value.getConvenio());
			client.setAgreement(agreement);
			client.setProcedure(procedure);

			logger.info("Consultando atendimento");
			Client clientTemp = dao.consultaToImport(value.getAtendimento(), procedure);

			if (clientTemp == null) {
				logger.info("Problema");
				logger.info("");
			} else {

				if (clientTemp.getId() != null) {
					logger.info("Atendimento encontrado, fazer update. Id: " + clientTemp.getId());
					client.setId(clientTemp.getId());
					client.setId_atend(value.getAtendimento());
					client.setCldate(value.getDate());
					client.setPacient(value.getPaciente());
					client.setOrigin(value.getOrigem());
					client.setMatsame(value.getAtendimento());
					client.setPg(1);
					client.setPgvalue(value.getValor());
					client.setRepasse(value.getRepasse());
					client.setTelephone(clientTemp.getTelephone());
					client.setService(clientTemp.getService());
				} else {
					logger.info("Atendimento nao encontrado, inserir atendimento");
					client.setId_atend(value.getAtendimento());
					client.setPacient(value.getPaciente());
					client.setCldate(value.getDate());
					client.setOrigin(value.getOrigem());
					client.setMatsame(value.getAtendimento());
					client.setPg(1);
					client.setPgvalue(value.getValor());
					client.setRepasse(value.getRepasse());
					client.setTelephone("0");
					client.setService("Registro inserido direto da planilha de recebimento");
				}
				logger.info("Salvando registro: " + regInsert++);
				logger.info("");
				dao.save(client);
			}

		}

	}

	private boolean checkProcedure(Long id, String description) throws Exception {
		logger.info("Pesquisando procediementto: " + id + ";" + description);
		ProcedureDao dao = new ProcedureDaoImpl();
		procedure = dao.consultaId(id);

		if (procedure != null) {
			logger.info("Procedimento encontrado");
			return true;
		} else {
			logger.info("Procedimento não encontrado");
			procedure = new Procedure();
			procedure.setId(id);
			procedure.setDescription(description);
			saveProcedure(id, description);
			procedure = dao.consultaId(id);
			logger.info("Procedimento inserido");
			return false;
		}
	}

	private boolean checkAgreement(String description) throws Exception {
		logger.info("Pesquisando convenio: " + description);
		AgreementDao dao = new AgreementDaoImpl();
		agreement = new Agreement();
		agreement = dao.consultaDescription(description);
		if (agreement != null) {
			logger.info("Convenio encontrado");
			return true;
		} else {
			logger.info("Convenio não encontrado");
			agreement = new Agreement();
			agreement.setDescription(description);
			dao.save(agreement);
			agreement = dao.consultaDescription(description);
			logger.info("Convenio inserido");
			return false;
		}
	}

	public void saveProcedure(Long id, String description) {
		try {
			String sql = "insert into ga_newprocedure (codigo, procdescription) values (" + id + ",'" + description + "');";
			HibernateUtility.beginTransaction();
			HibernateUtility.getSession().createSQLQuery(sql).executeUpdate();
			HibernateUtility.commitTransaction();
			HibernateUtility.closeSession();
		} catch (HibernateException hibernateException) {
			logger.info("Efetuando rollback da transação");
			HibernateUtility.rollbackTransaction();
			HibernateUtility.closeSession();
			logger.error("Erro ao salvar", hibernateException);
			throw hibernateException;
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

}
