package com.stock.app.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.stock.app.pojo.StockPriceHistoryObject;

/**
 * @author varun kasturi
 *
 */
public interface StockHistoryRepository extends PagingAndSortingRepository<StockPriceHistoryObject, Long>{	
	List<StockPriceHistoryObject> findAllByStockId(Long stockId);
}
