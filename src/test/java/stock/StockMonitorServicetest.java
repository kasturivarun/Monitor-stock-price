/**
 * 
 */
package stock;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stock.app.dao.StockHistoryRepository;
import com.stock.app.dao.StockMonitorDao;
import com.stock.app.dao.StockRepository;
import com.stock.app.pojo.StockObject;
import com.stock.app.pojo.StockPriceHistoryObject;
import com.stock.app.service.StockMonitorService;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

/**
 * @author varun kasturi
 *
 */
public class StockMonitorServicetest {

	StockMonitorService serviceFunction;
	String testSymbol = "TEST1";
	StockObject stockObjectTest = new StockObject();
	
	private StockMonitorDao dao;
	@Mock
	private StockRepository stockRepositoryMock;
	@Mock
	private StockHistoryRepository stockHistoryRepositoryMock;
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		dao = new StockMonitorDao(stockRepositoryMock, stockHistoryRepositoryMock);
		
		serviceFunction = new StockMonitorService();
		serviceFunction.setDao(dao);
	}
	
	@Test
	public void deleteSymbolTest(){
		
		
		stockObjectTest.setSymbol(testSymbol);
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(stockObjectTest);
		
		serviceFunction.deleteSymbol(testSymbol);
		
		verify(stockRepositoryMock,times(2)).findBySymbol(testSymbol);
		verify(stockRepositoryMock,times(1)).delete(stockObjectTest);
	}
	
	@Test
	public void getAllCompaniesTest(){
		
		serviceFunction.getAllCompanies();
		verify(stockRepositoryMock,times(1)).findAll();
	}
	
	@Test
	public void getSymbolTest(){
		stockObjectTest.setSymbol(testSymbol);
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(stockObjectTest);
		
		StockObject so = serviceFunction.getSymbol(testSymbol);
		
		verify(stockRepositoryMock,times(1)).findBySymbol(testSymbol);
		Assert.assertEquals(so,stockObjectTest);
	}
	
	@Test
	public void addSymbolTestFalse() {
		
		stockObjectTest.setSymbol("M");
		when(stockRepositoryMock.save(stockObjectTest)).thenReturn(stockObjectTest);
		when(stockRepositoryMock.findBySymbol("M")).thenReturn(stockObjectTest);
		
		Assert.assertFalse(serviceFunction.addSymbol("M"));
		
		verify(stockRepositoryMock,times(1)).findBySymbol("M");
		verify(stockRepositoryMock,times(0)).save(any(StockObject.class));
		
	}
	
	@Test
	public void addSymbolTestTrue() {
		
		stockObjectTest.setSymbol("M");
		when(stockRepositoryMock.save(any(StockObject.class))).thenReturn(stockObjectTest);
		when(stockRepositoryMock.findBySymbol("M")).thenReturn(null);
		
		Assert.assertTrue(serviceFunction.addSymbol("M"));
		
		verify(stockRepositoryMock,times(1)).findBySymbol("M");
		verify(stockRepositoryMock,times(1)).save(any(StockObject.class));
		
	}
	
	@Test
	public void updateStockHistoryPriceRecordTest() throws Exception{
		
		stockObjectTest.setCompanyName("testCompany");
		stockObjectTest.setPrice(new BigDecimal(100.0));
		stockObjectTest.setPriceHistoryRecords(new ArrayList<StockPriceHistoryObject>());
		stockObjectTest.setStockId(123);
		stockObjectTest.setSymbol("M");
		
		List<StockObject> companies = new ArrayList<StockObject>();
		companies.add(stockObjectTest);
		
		when(stockRepositoryMock.findAll()).thenReturn(companies);
		when(stockRepositoryMock.save(any(StockObject.class))).thenReturn(stockObjectTest);
		
		serviceFunction.updateStockHistoryPriceRecord();
		
		verify(stockRepositoryMock,times(1)).findAll();
		verify(stockHistoryRepositoryMock,times(1)).save(any(StockPriceHistoryObject.class));
		verify(stockRepositoryMock,times(1)).save(any(StockObject.class));
	}
	
}
