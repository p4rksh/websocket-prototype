package org.example.websocket.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class WebSocketDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "websocket.datasource.cluster")
    public HikariConfig dataSourceConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource(HikariConfig dataSourceConfig) {
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
