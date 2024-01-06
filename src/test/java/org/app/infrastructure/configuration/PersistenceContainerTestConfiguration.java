package org.app.infrastructure.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration
public class PersistenceContainerTestConfiguration {

    public static final String POSTGRES_USERNAME = "username";
    public static final String POSTGRES_PASSWORD = "password";
    public static final String POSTGRES_BEAN_NAME = "postgres";
    public static final String POSTGRES_CONTAINER = "postgres:15.5";

    @Bean
    @Qualifier(POSTGRES_BEAN_NAME)
    PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>(POSTGRES_CONTAINER)
                .withUsername(POSTGRES_USERNAME)
                .withPassword(POSTGRES_PASSWORD);
        container.start();
        return container;
    }

    @Bean
    DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }
}
