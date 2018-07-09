package com.andy.pfoWeb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.andy.pfoModel.Quote;
import com.andy.pfoModel.Stock;
import com.andy.pfoEjb.session.QuoteSession;
import com.andy.pfoEjb.session.StockSession;

@Named("QuoteListBean")
@SessionScoped
public class QuoteListBean implements Serializable {
	private static final long serialVersionUID = -3523180957944435853L;
	
	@Inject
	QuoteSession quoteSession;

	@Inject
	StockSession stockSession;
	
	String selectedSymbol;
	
	public QuoteListBean() {}
	
	public List<String> getSymbolList() {
		List<Stock> stockList = stockSession.findAll();
		List<String> symbolList = new ArrayList<String>();
		for (Stock stock : stockList) {
			symbolList.add(stock.getName());
		}
		return symbolList;
	}

	public String getSelectedSymbol() {
		return selectedSymbol;
	}

	public void setSelectedSymbol(String selectedSymbol) {
		this.selectedSymbol = selectedSymbol;
	}
	
	
	public List<QuoteItem> getQuoteList() {
		if (selectedSymbol == null) {
			return new ArrayList<QuoteItem>();
		}
		List<QuoteItem> quoteList = new ArrayList<QuoteItem>();
		Stock stock = stockSession.findByNameWithTimeline(selectedSymbol);
		Map<String,Double> timeline = stock.getTimelineQuote();
		List<String> keyList = new ArrayList<String>(timeline.keySet());
		keyList.sort(Comparator.naturalOrder());
		for(String date : keyList) {
			Quote q = new Quote(selectedSymbol, date, timeline.get(date));
			QuoteItem item = new QuoteItem(q);
			quoteList.add(item);
		}
		return quoteList;
	}
	

}
