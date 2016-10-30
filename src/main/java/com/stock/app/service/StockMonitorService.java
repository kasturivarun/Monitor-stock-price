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
import com.stock.app.pojo.StockPriceHistoryObject;

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
		if(result){
			StockPriceHistoryObject obj = new StockPriceHistoryObject();
			obj.setLastTradePrice(stock.getPrice());
			obj.setLastUpdateTime(new Date());
			obj.setStockId(stock);
			return dao.addPriceHistoryRecord(obj);
		}
		return false;
	}

	public Boolean deleteSymbol(String symbol) {
		return dao.deleteSymbol(symbol);
	}

	public List<StockObject> getAllCompanies() {
		return dao.getAllCompanies();
	}
	
	@Transactional
	@Scheduled(fixedRate = 300000)
    public void UpdateStockHistoryPriceRecord() throws Exception {
		
		List<StockObject> companies = getAllCompanies();
		for (StockObject company : companies) {
			try {
				Stock stockFromYahoo = YahooFinance.get(company.getSymbol());
				BigDecimal price = stockFromYahoo.getQuote().getPrice();
				company.setPrice(price);
			} catch (IOException e) {
				e.printStackTrace();
			}
			StockPriceHistoryObject obj = new StockPriceHistoryObject();
			obj.setLastTradePrice(company.getPrice());
			obj.setLastUpdateTime(new Date());
			obj.setStockId(company);
			dao.addPriceHistoryRecord(obj);
			List<StockPriceHistoryObject> recs = company.getPriceHistoryRecords();
			recs.add(obj);
			
			dao.updateSymbol(company);
		}

    }

}
