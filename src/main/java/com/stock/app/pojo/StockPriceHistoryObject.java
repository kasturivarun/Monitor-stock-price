/**
 * 
 */
package com.stock.app.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author varun kasturi
 *
 */
@Entity
public class StockPriceHistoryObject {

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "stockId", nullable = false)
	@JsonBackReference
	private StockObject stockId;
	 
	private BigDecimal lastTradePrice;
	 
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTime;

	public StockObject getStockId() {
		return stockId;
	}

	public void setStockId(StockObject stockObject) {
		this.stockId = stockObject;
	}

	public BigDecimal getLastTradePrice() {
		return lastTradePrice;
	}

	public void setLastTradePrice(BigDecimal lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
