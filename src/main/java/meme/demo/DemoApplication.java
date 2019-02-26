package meme.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
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
}
