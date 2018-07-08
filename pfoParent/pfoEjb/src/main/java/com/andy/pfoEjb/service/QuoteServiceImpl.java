package com.andy.pfoEjb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ejb.TransactionAttributeType;

import com.andy.pfoWebHelper.MessageBean;
import com.andy.pfoModel.Quote;

@Stateless
@Dependent
public class QuoteServiceImpl implements Serializable, QuoteService {
	private static final long serialVersionUID = 8319421301106539628L;
	private static Logger logger = Logger.getLogger("com.andy.service.QuoteServiceImpl");
	
	@PersistenceContext(unitName="portfolioUnit")
	EntityManager em;
	
	@Inject
	MessageBean messageBean;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persiste(Quote quote) {
		em.persist(quote);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Quote> findBySymbol(String symbol) {
		logger.info("QUOTESERVICE FINDBYSYMBOL CALLED WITH: " + symbol);
		List<Quote> quoteList = new ArrayList<Quote>();
		Query q = em.createQuery("select q from Quote q where q.symbol = :symbol");
		q.setParameter("symbol", symbol);
		try {
			logger.info("QUERY BEING CALLED");
			quoteList = (List<Quote>) q.getResultList();
			logger.info("QUERY EXECUTED");
		} catch (Exception ex) {
			messageBean.setMessage("DB access problem");
			return null;
		}
		return quoteList;
	}

}
