package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import br.com.models.Agreement;
import br.com.utils.HibernateUtility;

public class AgreementDaoImpl extends DaoGenericoImpl < Agreement , Long > implements AgreementDao {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public List consultar() {
		List lista = new ArrayList();
        Criteria crit = HibernateUtility.getSession().createCriteria(Agreement.class);
		crit.addOrder(Property.forName("description").asc());
        lista = (List) crit.list();
        return lista;
	}
	
	public Agreement consultaId(Long id) {
		List lista = new ArrayList();
		Criteria crit = HibernateUtility.getSession().createCriteria(Agreement.class);
		crit.add(Restrictions.eq("id", id));
		lista = (List) crit.list();
		if (lista.size() == 0) {
			return null;
		} else {
			Agreement agreement = (Agreement) lista.get(0);
			return agreement;
		}
	
	}

	public Agreement consultaDescription(String description) {
		List lista = new ArrayList();
		Criteria crit = HibernateUtility.getSession().createCriteria(Agreement.class);
		crit.add(Restrictions.eq("description", description));
		lista = (List) crit.list();
		if (lista.size() == 0) {
			return null;
		} else {
			Agreement agreement = (Agreement) lista.get(0);
			return agreement;
		}
	
	}

}
