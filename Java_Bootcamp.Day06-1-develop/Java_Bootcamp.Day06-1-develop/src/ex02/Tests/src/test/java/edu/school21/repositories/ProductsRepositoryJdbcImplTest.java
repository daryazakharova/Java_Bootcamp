package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    private ProductsRepositoryJdbcImpl repository;

    // Expected test data
    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "Product A", 10.99),
            new Product(2L, "Product B", 20.99),
            new Product(3L, "Product C", 30.99),
            new Product(4L, "Product D", 40.99),
            new Product(5L, "Product E", 50.99)
    );
    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Product A", 10.99);
    private final Product EXPECTED_UPDATED_PRODUCT = new Product(3L, "Updated Product C", 15.99);
    private final Product EXPECTED_DELETED_PRODUCT = new Product(1L, "Product A", 10.99);
    private final Product EXPECTED_SAVE_PRODUCT = new Product(6L, "Product F", 60.99);

    @BeforeEach
    public void init() {
        // Create database and initialize the repository
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        repository = new ProductsRepositoryJdbcImpl(jdbcTemplate);
    }

    @Test
    public void testFindAll() {
        List<Product> products = repository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
    }

    @Test
    public void testFindById() {
        Product product = repository.findById(1L).orElse(null);
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product);
    }

    @Test
    public void testUpdate() {
        repository.update(EXPECTED_UPDATED_PRODUCT );

        Product updatedProduct = repository.findById(3L).orElse(null);
        assertEquals(EXPECTED_UPDATED_PRODUCT , updatedProduct);
    }

    @Test
    public void testSave() {
        repository.save(EXPECTED_SAVE_PRODUCT);

        Product savedProduct = repository.findById(6L).orElse(null);
        assertEquals(EXPECTED_SAVE_PRODUCT, savedProduct);
    }

    @Test
    public void testDelete() {
        repository.delete(EXPECTED_DELETED_PRODUCT.getId());
        assertFalse(repository.findById(1L).isPresent());
    }
}
