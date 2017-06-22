package decamargo.tutorials.java.socket.main;

import decamargo.tutorials.java.socket.ws.RestFilter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("decamargo.tutorials.java.socket")
@EntityScan("decamargo.tutorials.java.socket.domain")
@EnableJpaRepositories(value = "decamargo.tutorials.java.socket.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public FilterRegistrationBean restFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new RestFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/rest/*");
        filterRegistration.setUrlPatterns(urlPatterns);
        return filterRegistration;
    }
}
