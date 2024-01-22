package fr.troisIl.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;



public class ProductDaoTest {

    private Database database;
    private Database mockDatabase;


    private ProductDao productDao;
    private ProductDao mockProductDao;
 
    @Before
    public void setUp() {
    	
        String testDatabaseFileName = "Product.db";

        // reset la BDD avant le test
        File file = new File(testDatabaseFileName);
        file.delete();

        database = new Database(testDatabaseFileName);
        database.createBasicSqlTable();

        mockDatabase = mock(Database.class);

        productDao = new ProductDao(database);
        mockProductDao= new ProductDao(mockDatabase);
        
    }

    
    @Test
    public void testInsert() throws SQLException {
        Product product = new Product();
        product.setLabel("Test Product");
        product.setQuantity(5);

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockDatabase.generatePrepared(anyString())).thenReturn(mockPreparedStatement);
        when(mockDatabase.executeSelect(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.getInt(1)).thenReturn(1);

        Product result = mockProductDao.insert(product);

        assertEquals(product, result);
        verify(mockDatabase, times(1)).generatePrepared(anyString());
        verify(mockDatabase, times(1)).executeSelect(anyString());
        verify(mockPreparedStatement, times(1)).setString(eq(1), eq("Test Product"));
        verify(mockPreparedStatement, times(1)).setInt(eq(2), eq(5));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }
    
    
    

    @Test
    public void testUpdate() throws SQLException {
        
        Product product = new Product();
        product.setId(1);
        product.setLabel("Updated Product");
        product.setQuantity(10);

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockDatabase.generatePrepared(anyString())).thenReturn(mockPreparedStatement);

        Product result = mockProductDao.update(product);

        assertEquals(product, result);
        verify(mockDatabase, times(1)).generatePrepared(anyString());
        verify(mockPreparedStatement, times(1)).setString(eq(1), eq("Updated Product"));
        verify(mockPreparedStatement, times(1)).setInt(eq(2), eq(10));
        verify(mockPreparedStatement, times(1)).setInt(eq(3), eq(1));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }

    /*@Test
    public void testFindById() throws SQLException {
         
        int productId = 1;
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockDatabase.generatePrepared(anyString())).thenReturn(mock(PreparedStatement.class));
        when(mockDatabase.executeSelect(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(productId);
        when(mockResultSet.getString("label")).thenReturn("Test Product");
        when(mockResultSet.getInt("quantity")).thenReturn(5);

        Product result = mockProductDao.findById(productId);

        //assertNotNull(result);
        assertEquals(productId, (int)result.getId());
        assertEquals("Test Product", result.getLabel());
        assertEquals(5, (int)result.getQuantity());
    }*/

    @Test
    public void testDelete() throws SQLException {
        int productId = 1;
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockDatabase.generatePrepared(anyString())).thenReturn(mockPreparedStatement);

        mockProductDao.delete(productId);

        verify(mockDatabase, times(1)).generatePrepared(anyString());
        verify(mockPreparedStatement, times(1)).setInt(eq(1), eq(productId));
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
    
   /* @Test
    public void testInsertAndFindById() {
        Product product = new Product();
        product.setLabel("Test Product");
        product.setQuantity(5);

        productDao.insert(product);

        Integer productId = product.getId();


        Product foundProduct = productDao.findById(productId);
        
        
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals("Test Product", foundProduct.getLabel());
        assertEquals(5, (int)foundProduct.getQuantity());
    }*/
    

}
