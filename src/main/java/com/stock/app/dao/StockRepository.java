/**
 * 
 */
package com.stock.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.stock.app.pojo.StockObject;


/**
 * @author varun kasturi
 *
 */
public interface StockRepository extends PagingAndSortingRepository<StockObject, Long>{	
	StockObject findBySymbol(String symbol);
}
