package com.andy.pfoModel;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="QUOTE")
@org.hibernate.annotations.Proxy(lazy = false)
public class Quote {
	@Id
	@GeneratedValue
	@Column(name="QUOTE_ID")
	Long id;
	
	@Column(name="SYMBOL")
	String symbol;
	
	@Column(name="DATUM")
	//@Temporal(value=TemporalType.DATE)
	String date;
	
	@Column(name="VALUE")
	Double value;
	
	public Quote() {}
	
	public Quote(String symbol, String date, Double value) {
		this.symbol = symbol;
		this.date = date;
		this.value = value;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	

}
