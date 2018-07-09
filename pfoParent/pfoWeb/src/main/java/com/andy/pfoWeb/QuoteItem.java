package com.andy.pfoWeb;

import com.andy.pfoModel.Quote;
import com.andy.pfoWebHelper.StringConverter;

public class QuoteItem {
	String symbol;
	String date;
	String value;
	StringConverter converter = new StringConverter();
	
	public QuoteItem(Quote quote) {
		this.symbol = quote.getSymbol();
		this.date = quote.getDate();
		Double dValue = quote.getValue();
		this.value = converter.fromDouble(dValue);		
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
