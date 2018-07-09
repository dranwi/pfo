package com.andy.pfoWeb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.andy.pfoModel.*;
import com.andy.pfoWebHelper.StringConverter;
import com.andy.pfoEjb.session.QuoteSession;

@SessionScoped
@Named("PortfolioViewBean")
public class PortfolioViewBean implements Serializable {
	private static final long serialVersionUID = 4746686264842756850L;
	private static Logger logger = Logger.getLogger("com.andy.portfolioWeb.PortfolioViewBean");
	
	List<TradeItem> tradeItemList;
	Portfolio portfolio;
	List<Stock> stockList;
	String rowClasses;
	StringConverter converter;
	String pfoTotalTradeSumString;
	String pfoTotalPresSumString;
	String pfoTotalProfitString;
	String pfoTotalMarginString;
	
	@Inject
	PortfolioManageBean pfoManageBean;
	
	@Inject
	QuoteSession quoteSession;

	public PortfolioViewBean() {
		converter = new StringConverter();
		
	}
	
	public String getTableTitel() {		
		portfolio = pfoManageBean.getSelectedPortfolio();
		String tableTitel = "Portfolio " + portfolio.getName() + " on " + converter.todayString();
		return tableTitel;
	}
	
	public List<TradeItem> getTradeItemList() {
		portfolio = pfoManageBean.getSelectedPortfolio();
		stockList = portfolio.getStockList();
		stockList.sort(new StockComparator());
		tradeItemList = new ArrayList<TradeItem>();
		Double pfoTotalTradeSum = 0.0;
		Double pfoTotalPresSum = 0.0;
		Integer color = 1;

		for (Stock s : stockList) {
			Double presQuote = 0.0;
			boolean presQuoteFound = false;
			Integer totalAmount = 0;
			Double totalTradeSum = 0.0;
			Double totalPresSum = 0.0;
			Double totalProfit = 0.0;
			Double totalMargin = 0.0;

			List<TradeItem> subTradeItemList = new ArrayList<TradeItem>();

			for (Trade t : s.getTradeList()) {
				TradeItem item = new TradeItem();

				// Date
				item.setDate(t.getDate());

				// Trade
				String tType = (t instanceof Purchase) ? "BUY" : "SELL";
				item.setTradeType(tType);

				// Symbol
				item.setSymbol(s.getName());

				// Currency
				item.setCurrency(t.getCurr());

				// Amount
				Integer amount = t.getAmount();
				if (t instanceof Sale) {
					amount = amount * (-1);
				}
				String amountString = converter.fromInteger(amount);
				item.setAmount(amountString);

				totalAmount = totalAmount + amount;

				// TradeQuote
				String tradeQuote = converter.fromDouble(t.getQuote());
				item.setTradeQuote(tradeQuote);

				// PresQuote
				String today = converter.todayString();
				if (!presQuoteFound) {
					presQuote = this.findQuote(s.getName(), today);
					presQuoteFound = true;
				}
				String presQuoteString = converter.fromDouble(presQuote);
				item.setPresQuote(presQuoteString);

				// TradeSum
				Double amountD = new Double(item.getAmount());
				Double tradeSum = amountD * t.getQuote();
				String tradeSumString = converter.fromInteger(tradeSum.intValue());
				item.setTradeSum(tradeSumString);

				totalTradeSum = totalTradeSum + tradeSum;
				pfoTotalTradeSum = pfoTotalTradeSum + tradeSum;

				// PresSum
				Double presSum = amountD * presQuote;
				String presSumString = converter.fromInteger(presSum.intValue());
				item.setPresSum(presSumString);

				totalPresSum = totalPresSum + presSum;
				pfoTotalPresSum = pfoTotalPresSum + presSum;

				// Profit
				Double profit = presSum - tradeSum;
				String profitString = converter.fromInteger(profit.intValue());
				item.setProfit(profitString);

				// Margin
				Double margin = profit / tradeSum;
				if(t instanceof Sale) {
					margin = margin * (-1.0);
				}
				String stringMargin = converter.fromMargin(margin);
				item.setMargin(stringMargin);

				subTradeItemList.add(item);
			}	//end Trade loop
			
			if (subTradeItemList.size() == 0) {
				continue;
			}
			
			color = color * (-1);
			this.setColor(subTradeItemList,color);

			subTradeItemList.sort(new TradeItemComparator());
			TradeItem firstItem = subTradeItemList.get(0);

			String totalAmountString = converter.fromInteger(totalAmount);
			firstItem.setTotalAmount(totalAmountString);

			String totalTradeSumString = converter.fromInteger(totalTradeSum.intValue());
			firstItem.setTotalTradeSum(totalTradeSumString);

			String totalPresSumString = converter.fromInteger(totalPresSum.intValue());
			firstItem.setTotalPresSum(totalPresSumString);

			totalProfit = totalPresSum - totalTradeSum;
			String totalProfitString = converter.fromInteger(totalProfit.intValue());
			firstItem.setTotalProfit(totalProfitString);

			totalMargin = totalProfit / totalTradeSum;
			String totalMarginString = converter.fromMargin(totalMargin);
			firstItem.setTotalMargin(totalMarginString);

			tradeItemList.addAll(subTradeItemList);
		}	// end Stock loop
		this.createRowClasses();
		pfoTotalTradeSumString = converter.fromInteger(pfoTotalTradeSum.intValue());
		pfoTotalPresSumString = converter.fromInteger(pfoTotalPresSum.intValue());
		pfoTotalProfitString = converter.fromInteger(new Double(pfoTotalPresSum - pfoTotalTradeSum).intValue());
		pfoTotalMarginString = converter.fromMargin(  new Double((pfoTotalPresSum - pfoTotalTradeSum) / pfoTotalTradeSum)  );
		return tradeItemList;	
	}
	
	public String getPfoTotalTradeSum() {
		return pfoTotalTradeSumString;
	}
	
	public String getPfoTotalPresSum() {
		return pfoTotalPresSumString;
	}
	
	public String getPfoTotalProfit() {
		return pfoTotalProfitString;
	}
	
	public String getPfoTotalMargin() {
		return pfoTotalMarginString;
	}
	
	void createRowClasses() {
		int length = tradeItemList.size();
		int counter = 0;
		StringBuilder sb = new StringBuilder("");
		String rowClass = null;
		for (TradeItem item : tradeItemList) {
			counter = counter + 1;
			if (item.getTradeType().equals("SELL")) {
				rowClass = " p";
			} else {
				rowClass = (item.getColor() == 1) ? " g" : " y";				
			}
			sb.append(rowClass);
			if (counter < length) {
				sb.append(",");
			}
		}
		rowClasses = sb.toString();	
		logger.info("ROW_CLASSES: " + rowClasses);
	}

	public String getRowClasses() {
		logger.info("GET_ROW_CLASSES CALLED");
		return rowClasses;
	}
	
	
	void setColor(List<TradeItem> tradeItemList, Integer color) {
		for(TradeItem item : tradeItemList) {
			item.setColor(color);
		}
	}
	
	Double findQuote(String symbol, String date) {
		List<Quote> quoteList = quoteSession.findBySymbol(symbol);
		for (Quote q : quoteList) {
			if (date.equals(q.getDate())) {
				return q.getValue();
			}
		}
		return 0.0;
	}
}
