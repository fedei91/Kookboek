package be.fedei91.kookboek;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KookboekApplication {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recepten")
                        .version("1.0.0")
                        .description("Toegang tot lekkere recepten")
                );
    }

    public static void main(String[] args) {
        SpringApplication.run(KookboekApplication.class, args);
    }

}
