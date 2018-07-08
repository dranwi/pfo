package com.andy.pfoEjb.vd;

import java.io.Serializable;
import java.time.LocalDate;

import javax.enterprise.context.RequestScoped;

import com.andy.pfoWebHelper.StringConverter;


@RequestScoped
public class QuoteDetailVD implements Serializable{
	private static final long serialVersionUID = -5825186213899033653L;
	
	String Symbol;
	String year;
	String month;
	String day;
	String currency;
	String value;
	StringConverter converter;
	
	public QuoteDetailVD() {
		LocalDate date = LocalDate.now();
		Integer y = date.getYear();
		Integer m = date.getMonthValue();
		Integer d = date.getDayOfMonth();
		year = String.valueOf(y);
		month = String.valueOf(m);
		day = String.valueOf(d);
		
	}

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
