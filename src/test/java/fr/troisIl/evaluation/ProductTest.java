package fr.troisIl.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class ProductTest {

    @Test
    public void testProductCreation() {
        Product product = new Product();
        assertNull(product.getId());
        assertNull(product.getLabel());
        assertNull(product.getQuantity());

        product.setId(1);
        product.setLabel("Test Product");
        product.setQuantity(10);

        assertEquals(1, (int)product.getId());
        assertEquals("Test Product", product.getLabel());
        assertEquals(10, (int)product.getQuantity());
    }
}