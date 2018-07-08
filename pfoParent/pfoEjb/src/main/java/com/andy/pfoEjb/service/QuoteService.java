package com.andy.pfoEjb.service;

import java.util.List;

import javax.ejb.Local;

import com.andy.pfoModel.Quote;

@Local
public interface QuoteService {
	public void persiste(Quote quote);
	public List<Quote> findBySymbol(String symbol);
}
