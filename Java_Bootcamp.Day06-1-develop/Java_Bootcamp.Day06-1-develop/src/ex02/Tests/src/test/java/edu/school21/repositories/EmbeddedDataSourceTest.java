package edu.school21.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmbeddedDataSourceTest {

    private DataSource dataSource;

    @BeforeEach
    void init() {
        // Create an embedded HSQLDB DataSource
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql") // Load the schema
                .addScript("data.sql")   // Load the test data
                .build();
    }

    @Test
    void testDataSourceConnection() throws SQLException {
        // Get a connection from the DataSource
        try (Connection connection = dataSource.getConnection()) {
            // Assert that the connection is not null
            assertNotNull(connection, "Connection should not be null");
        }
    }
}