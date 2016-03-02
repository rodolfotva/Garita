package br.com.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import br.com.models.Client;
import br.com.models.ClientSearch;
import br.com.models.Procedure;
import br.com.utils.HibernateUtility;

public class ClientDaoImpl extends DaoGenericoImpl<Client, Long> implements ClientDao {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public List consultar() {
		Calendar fileDate = Calendar.getInstance();
		Date today = fileDate.getTime();
		fileDate.add(Calendar.MONTH, -6);   
		Date sixMonth = fileDate.getTime();
		List lista = new ArrayList();
		Criteria crit = HibernateUtility.getSession().createCriteria(Client.class);
		crit.add(Restrictions.between("cldate", sixMonth, today));
		crit.addOrder(Property.forName("cldate").desc());
		lista = (List) crit.list();
		return lista;
	}

	public List consultarReport(ClientSearch client) {
		List lista = new ArrayList();
		Criteria crit = HibernateUtility.getSession().createCriteria(Client.class);
		if (!client.getName().equals("")) {
			crit.add(Restrictions.eq("pacient", client.getName()));
			logger.info("Nome: " + client.getName());
		}
		if (client.getPg() != -1) {
			crit.add(Restrictions.eq("pg", client.getPg()));
			logger.info("Pg: " + client.getPg());
		}
		if (!client.getRepasse().equals("")) {
			crit.add(Restrictions.eq("repasse", client.getRepasse()));
			logger.info("Repasse: " + client.getRepasse());
		}
		if (client.getAgreement() != null) {
			crit.add(Restrictions.eq("agreement", client.getAgreement()));
			logger.info("Convenio: " + client.getAgreement());
		}
		if (client.getProcedure() != null) {
			crit.add(Restrictions.eq("procedure", client.getProcedure()));
			logger.info("Procedimento: " + client.getProcedure());
		}
		if (client.getId_atend() != 0) {
			crit.add(Restrictions.eq("id_atend", client.getId_atend()));
			logger.info("Atendimento: " + client.getId_atend());
		}
		if (client.getInitialDate() != null) {
			if (client.getFinalDate() != null) {
				crit.add(Restrictions.between("cldate", client.getInitialDate(), client.getFinalDate()));
				logger.info("Data Inicial: " + client.getInitialDate());
				logger.info("Data Final: " + client.getFinalDate());
			}
		}

		crit.addOrder(Property.forName("cldate").desc());
		lista = (List) crit.list();
		return lista;
	}

	public Client consultaToImport(Long atendimento, Procedure procedure) {
		List lista = new ArrayList();
		List listaTmp = new ArrayList();
		Criteria crit = HibernateUtility.getSession().createCriteria(Client.class);
		crit.add(Restrictions.eq("id_atend", atendimento));
		lista = (List) crit.list();
		listaTmp = lista;
		if (lista.size() == 0) {
			return new Client();
		} else if (lista.size() == 1) {
			return (Client) lista.get(0);
		} else {
			crit.add(Restrictions.eq("procedure", procedure));
			lista = (List) crit.list();
			if (lista.size() == 1) {
				return (Client) lista.get(0);
			} else {
				logger.info("*************************************** PROBLEMA ***************************************");
				logger.info("Encontrado: ");
				for (Object value : listaTmp) {
					Client client = (Client) value;
					logger.info("Atendimento: " + client.getId_atend() + " Procedimento: ("+client.getProcedure().getId()+") " + client.getProcedure().getDescription());
				}

				logger.info("Tentando inserir: ");
				logger.info("Atendimento: " + atendimento + " Procedimento: ("+procedure.getId()+") " + procedure.getDescription());
				logger.info("*************************************** ###### FIM ######## ***************************************");
				return null;
			}
		}
	}
}
