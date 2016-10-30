/**
 * 
 */
package com.stock.app.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;




/**
 * @author varun kasturi
 *
 */
@Entity
public class StockObject {
	
	@Id 
	@GeneratedValue
	@Column(name = "stockId")
	private long stockId;
	
	@Column
	private String symbol;
	
	@Column
	private String companyName;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column
	private BigDecimal price;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stockId", orphanRemoval = true)
    @JsonManagedReference
    private List<StockPriceHistoryObject> priceHistoryRecords = new ArrayList<StockPriceHistoryObject>(0);
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String companyName) {
		this.symbol = companyName;
	}
	public List<StockPriceHistoryObject> getPriceHistoryRecords() {
		return priceHistoryRecords;
	}
	public void setPriceHistoryRecords(
			List<StockPriceHistoryObject> priceHistoryRecords) {
		this.priceHistoryRecords = priceHistoryRecords;
	}
}
