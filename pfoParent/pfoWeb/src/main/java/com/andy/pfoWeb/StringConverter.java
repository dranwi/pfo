package com.andy.pfoWeb;

import java.text.NumberFormat;
import java.util.Locale;

public class StringConverter {
	
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

}
