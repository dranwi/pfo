package com.andy.pfoEjb.vd;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class QuoteDetailVD implements Serializable{
	private static final long serialVersionUID = -5825186213899033653L;
	
	String Symbol;
	String year;
	String month;
	String day;
	String value;
	
	public QuoteDetailVD() {}

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
