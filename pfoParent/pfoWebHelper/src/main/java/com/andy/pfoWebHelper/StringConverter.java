package com.andy.pfoWebHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;


public class StringConverter {
	private static Logger logger = Logger.getLogger("com.andy.pfoWeb.StringConverter");
	
	public String fromInteger(Integer intValue) {
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		String stringInteger = nf.format(intValue);
		return stringInteger;		
	}
	
	public String fromDouble(Double dValue) {
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String quote = nf.format(dValue);
		return quote;
	}
	
	public String fromMargin(Double margin) {
		margin = margin * 100;
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String stringMargin = nf.format(margin);
		return stringMargin + "%";
	}
	
	public Double toDouble(String value) {
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		//DecimalFormat df = (DecimalFormat)nf;
		//df.applyPattern("####,##");
		Number number;
		try {
			number = nf.parse(value);			
		} catch (Exception ex) {
			logger.info("FORMAT EXEPTION FOR: " + value);
			return new Double(0.0);
		}
		Double d = (Double)number;
		return d;
		
	}
	
	public String todayString() {
		LocalDate date = LocalDate.now();

		String year = String.valueOf(date.getYear());
		String month = String.valueOf(date.getMonthValue());
		String day = String.valueOf(date.getDayOfMonth());
		
		DateString dateString =  new DateString(day, month, year);
		logger.info("TODAY STRING: " + dateString.getString());
		return dateString.getString();
	}

}
