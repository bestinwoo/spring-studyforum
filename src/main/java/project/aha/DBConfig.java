package project.aha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath://application.properties")
public class DBConfig {
    @Autowired
    ApplicationContext applicationContext;

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    public Hikari hikariConfig() {
//
//    }
}
