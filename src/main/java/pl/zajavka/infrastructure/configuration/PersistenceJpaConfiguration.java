package pl.zajavka.infrastructure.configuration;

import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.zajavka.infrastructure.database.entity.EntityMarker;
import pl.zajavka.infrastructure.database.repository.jpa.JpaRepositoriesMarker;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@AllArgsConstructor
@EnableTransactionManagement
@PropertySource({"classpath:database.properties"})
@EnableJpaRepositories(basePackageClasses = JpaRepositoriesMarker.class)
public class PersistenceJpaConfiguration {
//
//    private static final Map<String, Object> HIBERNATE_SETTINGS = Map.ofEntries(
//            Map.entry(Environment.DRIVER, "org.postgresql.Driver"),
//            Map.entry(Environment.URL, "jdbc:postgresql://localhost:5432/car_dealership"),
//            Map.entry(Environment.USER, "postgres"),
//            Map.entry(Environment.PASS, "postgres"),
//            Map.entry(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect"),
//            Map.entry(Environment.CONNECTION_PROVIDER, "org.hibernate.hikaricp.internal.HikariCPConnectionProvider"),
//            Map.entry(Environment.HBM2DDL_AUTO, "validate"),
//            Map.entry(Environment.SHOW_SQL, true),
//            Map.entry(Environment.FORMAT_SQL, false)
//    );
//
//    private static final Map<String, Object> HIKARI_CP_SETTINGS = Map.ofEntries(
//            Map.entry("hibernate.hikari.connectionTimeout", "20000"),
//            Map.entry("hibernate.hikari.minimumIdle", "10"),
//            Map.entry("hibernate.hikari.maximumPoolSize", "20"),
//            Map.entry("hibernate.hikari.idleTimeout", "300000")
//    );

    private final org.springframework.core.env.Environment environment;

    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(EntityMarker.class.getPackageName());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
    }

    private Properties jpaProperties() {
        final Properties properties = new Properties();

        return properties;
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setBaselineOnMigrate(true);
        configuration.setLocations(new Location("filesystem:src/main/resources/flyway/migrations"));
        configuration.setDataSource(dataSource());
        return new Flyway(configuration);
    }

    @Bean
    private DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.pass"));
        return dataSource;
    }
}
