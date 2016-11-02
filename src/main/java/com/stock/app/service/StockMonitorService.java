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
		StockObject newStock = dao.getSymbol(symbol);
		return newStock;
	}

	public Boolean addSymbol(String symbol) {
		StockObject stock = new StockObject();
		stock.setSymbol(symbol);
		
		try {
			Stock stockFromYahoo = YahooFinance.get(symbol);
			
			BigDecimal price = stockFromYahoo.getQuote().getPrice();
			
			if(price == null) {
				return false;
			}
			
			stock.setPrice(price);
			stock.setCompanyName(stockFromYahoo.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Boolean result = dao.addSymbol(stock);
		System.out.println(result);
		if(result){
			StockPriceHistoryObject obj = new StockPriceHistoryObject();
			obj.setLastTradePrice(stock.getPrice());
			obj.setLastUpdateTime(new Date());
			obj.setStockId(stock);
			dao.addPriceHistoryRecord(obj);
			return true;
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
	@Scheduled(fixedRate = 400000)
    public void updateStockHistoryPriceRecord() throws Exception {
		
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

	public StockMonitorDao getDao() {
		return dao;
	}

	public void setDao(StockMonitorDao dao) {
		this.dao = dao;
	}


}
