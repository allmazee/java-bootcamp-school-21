package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    ProductsRepository productsRepository;
    EmbeddedDatabase dataSource;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "product1", 100),
            new Product(2L, "product2", 0),
            new Product(3L, "product3", 500),
            new Product(4L, "product4", 399),
            new Product(5L, "product5", 200)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(
            1L, "product1", 100);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(
            2L, "new-product2", 150);

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        try {
            productsRepository = new ProductsRepositoryJdbcImpl(
                    dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        dataSource.shutdown();
    }

    @Test
    public void testFindAll() {
        List<Product> products = productsRepository.findAll();
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
    }

    @Test
    public void testFindById() {
        Optional<Product> product = productsRepository.findById(1L);
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product.get());
    }

    @Test
    public void testUpdate() {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> product = productsRepository.findById(2L);
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, product.get());
    }

    @Test
    public void testSave() {
        int sizeBefore = productsRepository.findAll().size();
        productsRepository.save(new Product(null, "product6", 999));
        int sizeAfter = productsRepository.findAll().size();
        Assertions.assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    public void testDelete() {
        productsRepository.delete(1L);
        Optional<Product> product = productsRepository.findById(1L);
        Assertions.assertFalse(product.isPresent());
    }
}
