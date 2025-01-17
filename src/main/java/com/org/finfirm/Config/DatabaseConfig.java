package com.org.finfirm.Config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.org.finfirm.repository")
public class DatabaseConfig implements WebMvcConfigurer {

    private static String[] packagesToScan = new String[] {"com.org.finfirm"};

    @Primary
    @Bean
    public DataSource dataSource() {
        // Configure HikariCP
        HikariConfig hikariConfig = new HikariConfig();

        // PostgreSQL JDBC connection details (directly defined)
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/Finfirm");  // Replace with your PostgreSQL URL
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setUsername("postgres");  // Replace with your database username
        hikariConfig.setPassword("Pass@sales");  // Replace with your database password

        // Set additional properties for Hikari if needed (e.g., pool size)
        hikariConfig.setMaximumPoolSize(10);  // Example pool size

        // Return the HikariDataSource instance
        return new HikariDataSource(hikariConfig);
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // Configure the JPA EntityManagerFactory
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        // Set the data source for the factory
        entityManagerFactoryBean.setDataSource(dataSource());

        // Use Hibernate as the JPA vendor
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Set the package(s) to scan for JPA entities
        entityManagerFactoryBean.setPackagesToScan(packagesToScan);

        // Set additional Hibernate properties
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");  // Example Hibernate setting
        properties.setProperty("hibernate.show_sql", "true");  // Show SQL in logs (optional)
        entityManagerFactoryBean.setJpaProperties(properties);

        return entityManagerFactoryBean;
    }

    @Primary
    @Bean
    public JpaTransactionManager transactionManager() {
        // Configure the JPA transaction manager
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }


}
