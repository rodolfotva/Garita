package br.com.utils;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.models.Agreement;
import br.com.models.Autorizacao;
import br.com.models.Client;
import br.com.models.Procedure;
import br.com.models.User;

public class HibernateUtility {
	
	private static Logger logger = Logger.getLogger(HibernateUtility.class);

	private static final SessionFactory factory;
	private static final ThreadLocal sessionThread = new ThreadLocal();
	private static final ThreadLocal transactionThread = new ThreadLocal();

	public static Session getSession() {
		Session session = (Session) sessionThread.get();
		if ((session == null) || (!(session.isOpen()))) {
			session = factory.openSession();
			sessionThread.set(session);
		}
		return ((Session) sessionThread.get());
	}

	public static void closeSession() {
		Session session = (Session) sessionThread.get();
		if ((session != null) && (session.isOpen())) {
			sessionThread.set(null);
			session.close();
		}
	}

	public static void beginTransaction() {
		Transaction transaction = getSession().beginTransaction();
		transactionThread.set(transaction);
	}

	public static void commitTransaction() {
		Transaction transaction = (Transaction) transactionThread.get();
		if ((transaction != null) && (!(transaction.wasCommitted())) && (!(transaction.wasRolledBack()))) {
			transaction.commit();
			transactionThread.set(null);
		}
	}

	public static void rollbackTransaction() {
		Transaction transaction = (Transaction) transactionThread.get();
		if ((transaction != null) && (!(transaction.wasCommitted())) && (!(transaction.wasRolledBack()))) {
			transaction.rollback();
			transactionThread.set(null);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		SessionFactory sessionFactory = null;
			try {
				AnnotationConfiguration ac = new AnnotationConfiguration();
				ac.addAnnotatedClass(Procedure.class);
				ac.addAnnotatedClass(Agreement.class);
				ac.addAnnotatedClass(Client.class);
				ac.addAnnotatedClass(User.class);
				ac.addAnnotatedClass(Autorizacao.class);
				sessionFactory = ac.configure().buildSessionFactory();
			} catch (Throwable ex) {
				logger.error("Erro ao resgatar sessão", ex);
				throw new ExceptionInInitializerError(ex);
			}
			return sessionFactory;
	} 

	static {
		try {
			factory = getSessionFactory();
		} catch (RuntimeException e) {
			logger.error("Erro ao resgatar sessão", e);
			throw e;
		}
	}
	
	
	public static void main(String[] args) {

	}

}
