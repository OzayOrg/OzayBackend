package com.ozay.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile(Constants.SPRING_PROFILE_HEROKU)
public class HerokuDatabaseConfiguration implements EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(HerokuDatabaseConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    @Bean
    public DataSource dataSource() {
        log.debug("Configuring Heroku Datasource");

        //String herokuUrl = propertyResolver.getProperty("heroku-url");
        String herokuUrl = System.getenv("DATABASE_URL");
        if (herokuUrl != null) {
            log.info("Using Heroku, parsing their $DATABASE_URL to use it with JDBC");
            URI dbUri = null;
            try {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            } catch (URISyntaxException e) {
                throw new ApplicationContextException("Heroku database connection pool is not configured correctly");
            }
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            /*String dbUrl = "jdbc:postgresql://" +
                    dbUri.getHost() +
                    ':' +
                    dbUri.getPort() +
                    dbUri.getPath() +
                    "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";*/
           String dbUrl = "jdbc:postgresql://ec2-54-83-10-210.compute-1.amazonaws.com/d7iegu76smkaqb?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";


            HikariConfig config = new HikariConfig();
<<<<<<< HEAD
            config.setMaximumPoolSize(3);
=======
            config.setMaximumPoolSize(5);
>>>>>>> a8b2aa70565282c3e90d36f80b034204f2487b86
            //config.setDataSourceClassName(propertyResolver.getProperty("dataSourceClassName"));
            //config.addDataSourceProperty("url", dbUrl);
            //config.addDataSourceProperty("user", username);
            //config.addDataSourceProperty("password", password);

            config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
            config.addDataSourceProperty("url", dbUrl);
<<<<<<< HEAD
            config.addDataSourceProperty("user", "nmxwrlceuoscjn");
            config.addDataSourceProperty("password", "F0xhGbhxZGzZjz5_PcBu6m6XEo");
=======
            config.addDataSourceProperty("user", "pymqxayzqcupig");
            config.addDataSourceProperty("password", "bbfQfAnPae6Fk4jrJDChq4qOpN");
>>>>>>> a8b2aa70565282c3e90d36f80b034204f2487b86



            return new HikariDataSource(config);
        } else {
            throw new ApplicationContextException("Heroku database URL is not configured, you must set --spring.datasource.heroku-url=$DATABASE_URL");
        }
    }
}
