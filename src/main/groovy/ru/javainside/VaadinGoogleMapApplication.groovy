package ru.javainside

import com.vaadin.server.VaadinServlet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource

import javax.sql.DataSource

@SpringBootApplication
@PropertySource('classpath:application.properties')
class VaadinGoogleMapApplication {

    @Autowired
    Environment environment

    static void main(String[] args) {
        SpringApplication.run VaadinGoogleMapApplication, args
    }

    @Bean
    DataSource dataSource() {
        def source = new DriverManagerDataSource(
                environment.getProperty("jdbc.host"),
                environment.getProperty("jdbc.login"),
                environment.getProperty("jdbc.password")
        )
        source.setDriverClassName(environment.getProperty("jdbc.driver"))
        return source
    }

}


