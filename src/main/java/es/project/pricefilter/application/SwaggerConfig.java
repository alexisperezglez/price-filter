package es.project.pricefilter.application;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${info.app.description}") String appDescription,
            @Value("${spring.app.version}") String appVersion,
            @Value("${spring.application.name}") String appName,
            @Value("${info.app.contact-name}") String appContactName,
            @Value("${info.app.contact-mail}") String appContactMail,
            @Value("${info.app.business-name}") String appBusinessName,
            @Value("${info.app.business-web}") String appBusinessWeb
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title(appName)
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name(appBusinessName).url(appBusinessWeb))
                        .contact(new Contact().name(appContactName).email(appContactMail)));
    }
}
