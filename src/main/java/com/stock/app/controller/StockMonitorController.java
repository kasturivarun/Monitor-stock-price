/**
 * 
 */
package com.stock.app.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.app.pojo.StockObject;
import com.stock.app.service.StockMonitorService;

/**
 * @author varun kasturi
 *
 */


@RestController
public class StockMonitorController {

	@Autowired
	StockMonitorService service;
	
	@RequestMapping("/getStock")
    public StockObject getSymbol(@RequestParam(value="symbol") String symbol) throws SQLException {
        return service.getSymbol(symbol);
    }
	
	@RequestMapping("/addCompany")
    public Boolean addSymbol(@RequestParam(value="symbol") String symbol) throws SQLException {
        return service.addSymbol(symbol);
    }
	
	@RequestMapping("/deleteCompany")
    public Boolean deleteSymbol(@RequestParam(value="symbol") String symbol) throws SQLException {
        return service.deleteSymbol(symbol);
    }
	
	
}
