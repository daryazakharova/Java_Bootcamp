package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductsRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", new ProductRowMapper());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM product WHERE identifier = ?", new Object[]{id}, new ProductRowMapper())
                .stream().findFirst();
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update("UPDATE product SET name = ?, price = ? WHERE identifier = ?",
                product.getName(), product.getPrice(), product.getId());
    }

    @Override
    public void save(Product product) {
        jdbcTemplate.update("INSERT INTO product (identifier, name, price) VALUES (?, ?, ?)",
                product.getId(), product.getName(), product.getPrice());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM product WHERE identifier = ?", id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long identifier = rs.getLong("identifier");
            String name = rs.getString("name");
            Double price = rs.getDouble("price");
            return new Product(identifier, name, price);
        }
    }
}