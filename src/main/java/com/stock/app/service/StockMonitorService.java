/**
 * 
 */
package com.stock.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.stock.app.dao.StockMonitorDao;
import com.stock.app.pojo.StockObject;

/**
 * @author varun kasturi
 *
 */
@Service
public class StockMonitorService {
	
	@Autowired
	StockMonitorDao dao;
	
	public StockObject getSymbol(String symbol){
		return dao.getSymbol(symbol);
	}

	public Boolean addSymbol(String symbol) {
		StockObject stock = new StockObject();
		stock.setSymbol(symbol);
		try {
			
			Stock stockFromYahoo = YahooFinance.get(symbol);
			BigDecimal price = stockFromYahoo.getQuote().getPrice();
			stock.setPrice(price);
			stock.setCompanyName(stockFromYahoo.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Boolean result = dao.addSymbol(stock);
		
		return result;
	}

}
