package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import br.com.models.Agreement;
import br.com.models.Client;
import br.com.models.Procedure;
import br.com.utils.HibernateUtility;

public class ProcedureDaoImpl extends DaoGenericoImpl<Procedure, Long> implements ProcedureDao {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public List consultar() {
		List lista = new ArrayList();
		Criteria crit = HibernateUtility.getSession().createCriteria(Procedure.class);
		crit.addOrder(Property.forName("description").asc());
		lista = (List) crit.list();
		return lista;
	}

	public Procedure consultaId(Long id) {
		List lista = new ArrayList();
		Criteria crit = HibernateUtility.getSession().createCriteria(Procedure.class);
		crit.add(Restrictions.eq("id", id));
		lista = (List) crit.list();
		if (lista.size() == 0) {
			return null;
		} else {
			Procedure procedure = (Procedure) lista.get(0);
			return procedure;
		}
	}

}
