/**
 * 
 */
package com.stock.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(StockMonitorService.class);
	
	public StockObject getSymbol(String symbol){
		log.info("Getting stock record {}", symbol);
		StockObject newStock = dao.getSymbol(symbol);
		return newStock;
	}

	public Boolean addSymbol(String symbol) {
		StockObject stock = new StockObject();
		log.info("Adding stock record {}", symbol);
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
		log.info("Deleting stock record {}", symbol);
		return dao.deleteSymbol(symbol);
	}

	public List<StockObject> getAllCompanies() {
		log.info("Getting all companies details");
		return dao.getAllCompanies();
	}
	
	@Transactional
	@Scheduled(fixedRate = 300000)
    public void updateStockHistoryPriceRecord() throws Exception {
		log.info("Running Cron job and updating database tables");
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
