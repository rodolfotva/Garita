package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.models.Procedure;
import br.com.models.User;
import br.com.utils.HibernateUtility;


public class UserDaoImpl extends DaoGenericoImpl < User , Long > implements UserDao {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public List consultar() {
        List lista = new ArrayList();
        Criteria crit = HibernateUtility.getSession().createCriteria(User.class);

        lista = (List) crit.list();
        return lista;
	}
	
	public List searchByID(String login){
		Criteria crit = HibernateUtility.getSession().createCriteria(User.class);
		crit.add(Restrictions.eq("login", login));
		return crit.list();
	}

}
