package com.andy.pfoWeb;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.andy.pfoModel.DateString;
import com.andy.pfoModel.Quote;
import com.andy.pfoModel.Stock;
import com.andy.pfoEjb.session.QuoteSession;
import com.andy.pfoEjb.session.StockSession;
import com.andy.pfoEjb.vd.QuoteDetailVD;

@Named("QuoteCreateBean")
@SessionScoped
public class QuoteCreateBean implements Serializable{
	private static final long serialVersionUID = -1357449093077947576L;
	private static Logger logger = Logger.getLogger("com.andy.portfolioWeb.QuoteCreateBean");
	
	@Inject 
	StockSession stockSession;
	
	@Inject
	QuoteSession quoteSession;
	
	@Inject
	QuoteDetailVD quoteDetailVD;
	
	DateFormat dateFormat;
	List<String> symbolList;
	String year;
	String month;
	String day;
	String value;
	
	public QuoteCreateBean() {
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	}
	
	
	public String quoteAction() throws Exception{
		String symbol = quoteDetailVD.getSymbol();
		String date = this.makeDate(quoteDetailVD);
		Double value = this.makeValue(quoteDetailVD);
		Quote quote = new Quote(symbol,date,value);
		quoteSession.createQuote(quote);		
		return "QUOTE_CREATED";		
	}
	
	Double makeValue(QuoteDetailVD vd) {
		Double d = Double.valueOf(vd.getValue());
		return d;
	}
	
	String makeDate(QuoteDetailVD vd) throws Exception{
		String year = vd.getYear();
		String month = vd.getMonth();
		String day = vd.getDay();
		String date = new DateString(day,month,year).getString();
		return date;
	}
	
	public List<String> getSymbolList() {
		symbolList = new ArrayList<String>();
		List<Stock> stockList = stockSession.findAll();
		logger.info("STOCKLIST SIZE: " + stockList.size());
		for (Stock stock : stockList) {
			symbolList.add(stock.getName());
			logger.info("ADDED SYMBOL " +  stock.getName());
		}
		return symbolList;		
	}


	public String getSelectedSymbol() {
		return quoteDetailVD.getSymbol();
	}


	public void setSelectedSymbol(String selectedSymbol) {
		quoteDetailVD.setSymbol(selectedSymbol);
	}


	public String getYear() {
		return quoteDetailVD.getYear();
	}


	public void setYear(String year) {
		quoteDetailVD.setYear(year);
	}


	public String getMonth() {
		return quoteDetailVD.getMonth();
	}


	public void setMonth(String month) {
		quoteDetailVD.setMonth(month);
	}


	public String getDay() {
		return quoteDetailVD.getDay();
	}


	public void setDay(String day) {
		quoteDetailVD.setDay(day);
	}


	public String getValue() {
		return quoteDetailVD.getValue();
	}


	public void setValue(String value) {
		quoteDetailVD.setValue(value);
	}
	
	

}
