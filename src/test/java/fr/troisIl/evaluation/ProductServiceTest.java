package fr.troisIl.evaluation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ProductServiceTest {

    private Database db = null;
    private Database mockDatabase;

    private ProductService productService;
    private ProductService mockProductService;
    private int countBefore = 0;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() throws SQLException {
        String testDatabaseFileName = "Product.db";

        // reset la BDD avant le test
        File file = new File(testDatabaseFileName);
        file.delete();

        db = new Database(testDatabaseFileName);
        db.createBasicSqlTable();
        mockDatabase = mock(Database.class);
        mockProductService = new ProductService(mockDatabase);

        
        productService = new ProductService(db);

        countBefore = count();
    }

    /**
     * Compte les produits en BDD
     *
     * @return le nombre de produit en BDD
     */
    private int count() throws SQLException {
        ResultSet resultSet = db.executeSelect("Select count(*) from Product");
        assertNotNull(resultSet);
        return resultSet.getInt(1);
    }

    /*@Test
    public void testInsert() throws SQLException {
        Product mockProduct = new Product();
        mockProduct.setLabel("Test Product");
        mockProduct.setQuantity(10);

        Product result = productService.insert(mockProduct);

        assertNotNull(result.getId());
    }*/

    /*@Test
    public void testUpdate() throws SQLException {
        Product mockProduct = new Product();
        mockProduct.setId(1); 
        mockProduct.setLabel("Updated Product");
        mockProduct.setQuantity(20);

        Product result = productService.update(mockProduct);

        assertEquals(mockProduct.getLabel(), result.getLabel());
        assertEquals(mockProduct.getQuantity(), result.getQuantity());
    }*/

    @Test
    public void testFindById() throws SQLException {
        Integer productId = 1;

        Product result = productService.findById(productId);

        assertNotNull(result);
        //assertEquals(productId, result.getId());
    }

    /*@Test
    public void testDelete() throws SQLException {
        Integer productId = 1;

        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Le produit n'a pas été trouvé en BDD");

        productService.delete(productId);
    }*/

}
