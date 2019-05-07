package mediamanager;

import mediamanager.repository.MediaFileEventHandler;
import mediamanager.service.FileStorageService;
import mediamanager.service.LocalFileStorageService;
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
    @Profile("production")
    @ConfigurationProperties("app.datasource.production")
    DataSource productionDataSource() {
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
    @Profile({"sandbox", "test"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "create");
        jpaProperties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");

        return createLocalContainerEntityManagerFactoryBean(dataSource, vendorAdapter, jpaProperties);
    }

    @Bean(name = "entityManagerFactory")
    @Profile({"production"})
    public LocalContainerEntityManagerFactoryBean productionEntityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");

        return createLocalContainerEntityManagerFactoryBean(dataSource, vendorAdapter, jpaProperties);
    }

    private LocalContainerEntityManagerFactoryBean createLocalContainerEntityManagerFactoryBean(DataSource dataSource, HibernateJpaVendorAdapter vendorAdapter, Map<String, String> jpaProperties) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("mediamanager");
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
