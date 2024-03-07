package restaurant.GrandmasFood.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("restaurant")
                .packagesToScan("restaurant.GrandmasFood.webApi")
                .build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Grandma's food restaurant API")
                        .version("1.0.0")
                        .description("This is an API used for managing restaurant clients, products & orders")
                        .contact(new Contact()
                                .name("@SpringSquad")
                                .email("juanfelipe.pena@globant.com")));
    }
}