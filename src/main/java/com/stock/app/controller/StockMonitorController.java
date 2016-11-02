/**
 * 
 */
package com.stock.app.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.app.pojo.StockObject;
import com.stock.app.pojo.StockPriceHistoryObject;
import com.stock.app.service.StockMonitorService;

/**
 * @author varun kasturi
 *
 */


@RestController
public class StockMonitorController {

	@Autowired
	StockMonitorService service;
	
	@RequestMapping(value= "/company/{symbol}", method = RequestMethod.GET)
    public List<StockPriceHistoryObject> getSymbol(@PathVariable String symbol) throws SQLException {
		List<StockPriceHistoryObject> list = service.getSymbol(symbol).getPriceHistoryRecords();
        return list;
    }
	
	@RequestMapping(value= "/company/{symbol}", method = RequestMethod.POST)
    public Boolean addSymbol(@PathVariable String symbol) throws SQLException {
        return service.addSymbol(symbol);
    }
	
	@RequestMapping(value= "/company/{symbol}", method = RequestMethod.DELETE)
    public Boolean deleteSymbol(@PathVariable String symbol) throws SQLException {
        return service.deleteSymbol(symbol);
    }
	
	@RequestMapping(value= "/company", method = RequestMethod.GET)
    public List<StockObject> getAllCompanies() throws SQLException {
        return service.getAllCompanies();
    }	
	
}
