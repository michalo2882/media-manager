package meme.demo;

import meme.demo.repository.MediaFileEventHandler;
import meme.demo.service.FileStorageService;
import meme.demo.service.LocalFileStorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    @Profile("sandbox")
    @ConfigurationProperties("app.datasource.sandbox")
    DataSource sandboxDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Profile("test")
    @ConfigurationProperties("app.datasource.test")
    DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    FileStorageService fileStorageService() {
        return new LocalFileStorageService();
    }

    @Bean
    MediaFileEventHandler mediaFileEventHandler() {
        return new MediaFileEventHandler();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        jpaProperties.put("hibernate.hbm2ddl.import_files", "/import.sql");
        jpaProperties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("meme.demo");
        factory.setDataSource(dataSource);
        factory.setJpaPropertyMap(jpaProperties);
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
