package br.com.fiap.mercadoApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(
    info = @Info(
        title = "Mercado API",
        version = "v1",
        description = "API do projeto mercado",
        contact = @Contact(
            name = "Felipe Fidelix",
            email = "felipe.elucidator@gmail.com"
        )
    )
)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
