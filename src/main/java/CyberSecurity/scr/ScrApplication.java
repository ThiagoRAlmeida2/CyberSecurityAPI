package CyberSecurity.scr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("CyberSecurity.scr.repository")  // Certifique-se de usar o pacote correto
public class ScrApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScrApplication.class, args);
    }
}
