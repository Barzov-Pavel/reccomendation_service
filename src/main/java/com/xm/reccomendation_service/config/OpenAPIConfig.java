package com.xm.reccomendation_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${recommendation.service.openapi.dev.url}")
    private String devUrl;
    @Value("${recommendation.service.openapi.email}")
    private String contactEmail;
    @Value("${recommendation.service.openapi.name}")
    private String contactName;
    @Value("${recommendation.service.openapi.dev.server.description}")
    private String devServerDescription;
    @Value("${recommendation.service.openapi.title}")
    private String title;
    @Value("${recommendation.service.openapi.version}")
    private String version;
    @Value("${recommendation.service.openapi.application.description}")
    private String applicationDescription;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription(devServerDescription);

        Contact contact = new Contact();
        contact.setEmail(contactEmail);
        contact.setName(contactName);

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title(title)
                .version(version)
                .contact(contact)
                .description(applicationDescription)
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
