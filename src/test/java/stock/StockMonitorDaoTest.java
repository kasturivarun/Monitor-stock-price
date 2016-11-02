/**
 * 
 */
package stock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

/**
 * @author varun kasturi
 *
 */
public class StockMonitorDaoTest {
	
	@Mock
	private StockRepository stockRepositoryMock;
	@Mock
	private StockHistoryRepository stockHistoryRepositoryMock;
	
	private StockMonitorDao dao;
	String testSymbol = "TEST1";
	StockObject stockObjectTest;
	StockPriceHistoryObject stockPriceHistoryObjectTest;
	List<StockObject> stockObjectListTest;
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		stockObjectTest = new StockObject();
		stockObjectListTest = new ArrayList<StockObject>();
		stockPriceHistoryObjectTest = new StockPriceHistoryObject();
		dao = new StockMonitorDao(stockRepositoryMock, stockHistoryRepositoryMock);
	}
	
	@Test
	public void getSymbolTest() {
		stockObjectTest.setSymbol(testSymbol);
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(stockObjectTest);
		
		Assert.assertEquals(stockObjectTest, dao.getSymbol(testSymbol));
		verify(stockRepositoryMock,times(1)).findBySymbol(testSymbol);
	}
	
	@Test
	public void updateSymbolTestSuccess() {
		stockObjectTest.setSymbol(testSymbol);
		when(stockRepositoryMock.save(stockObjectTest)).thenReturn(stockObjectTest);
		
		Assert.assertTrue(dao.updateSymbol(stockObjectTest));
		
		verify(stockRepositoryMock,times(1)).save(stockObjectTest);
	}
	
	@Test
	public void updateSymbolTestFail() {
		
		when(stockRepositoryMock.save(stockObjectTest)).thenReturn(null);
		
		Assert.assertFalse(dao.updateSymbol(stockObjectTest));
		
		verify(stockRepositoryMock,times(1)).save(stockObjectTest);
	}
	
	@Test
	public void addSymbolTestFailure1(){
		stockObjectTest.setSymbol(testSymbol);
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(stockObjectTest);
		Assert.assertFalse(dao.addSymbol(stockObjectTest));
		verify(stockRepositoryMock,times(0)).save(stockObjectTest);
	}
	
	@Test
	public void addSymbolTestFailure2(){
		stockObjectTest.setSymbol(testSymbol);
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(null);
		when(stockRepositoryMock.save(stockObjectTest)).thenReturn(null);
		Assert.assertFalse(dao.addSymbol(stockObjectTest));
		verify(stockRepositoryMock,times(1)).save(stockObjectTest);
	}
	
	@Test
	public void addSymbolTestSuccess(){
		stockObjectTest.setSymbol(testSymbol);
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(null);
		when(stockRepositoryMock.save(stockObjectTest)).thenReturn(stockObjectTest);
		Assert.assertTrue(dao.addSymbol(stockObjectTest));
		verify(stockRepositoryMock,times(1)).save(stockObjectTest);
	}
	
	@Test
	public void deleteSymbolTestSuccess(){
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(stockObjectTest, (StockObject)null);
		Assert.assertTrue(dao.deleteSymbol(testSymbol));
		verify(stockRepositoryMock,times(1)).delete(stockObjectTest);
	}
	
	@Test
	public void deleteSymbolTestFail(){
		when(stockRepositoryMock.findBySymbol(testSymbol)).thenReturn(stockObjectTest, stockObjectTest);
		Assert.assertFalse(dao.deleteSymbol(testSymbol));
		verify(stockRepositoryMock,times(1)).delete(stockObjectTest);
	}

	@Test
	public void addPriceHistoryRecordTest(){
		dao.addPriceHistoryRecord(stockPriceHistoryObjectTest);
		verify(stockHistoryRepositoryMock,times(1)).save(stockPriceHistoryObjectTest);
	}
	
	@Test
	public void getAllCompaniesTest(){
		when(stockRepositoryMock.findAll()).thenReturn(stockObjectListTest);
		
		Assert.assertEquals(dao.getAllCompanies(),stockObjectListTest);
	}
}
