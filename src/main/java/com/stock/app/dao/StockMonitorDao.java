/**
 * 
 */
package com.stock.app.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.stock.app.pojo.StockObject;

/**
 * @author varun kasturi
 *
 */
@Service
public class StockMonitorDao {

	
	
	
	private StockRepository stockRepository;
	
	
	public StockObject getSymbol(String symbol){
		StockObject st = stockRepository.findBySymbol(symbol);
		return st;
	}

	
	
	@Transactional
	public Boolean addSymbol(StockObject stock) {
		
		StockObject st = stockRepository.findBySymbol(stock.getSymbol());
		
		if(st != null){
			return false;
		}
		
		StockObject saved = stockRepository.save(stock);
		if(saved == null){
			return false;
		}else{
			return true;			
		}
	}

	@Transactional
	public Boolean deleteSymbol(String symbol) {
		StockObject st = stockRepository.findBySymbol(symbol);
		stockRepository.delete(st);
		st = stockRepository.findBySymbol(symbol);
		if(st == null){
			return true;
		}else{
			return false;			
		}
	}
	
	
	
}
