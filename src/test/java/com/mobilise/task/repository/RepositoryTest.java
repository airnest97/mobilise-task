package com.mobilise.task.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class RepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Test
    void testThatDataSourceExists() {
        assertNotNull(dataSource);
        try {
            Connection connection = dataSource.getConnection();
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("MOBILISEDB");
            log.info("Catalog --> {}", connection.getCatalog());
        } catch (SQLException exception) {
            log.info("An Error occurred --> {}", exception.toString());
        }
    }
}
