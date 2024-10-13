package ru.effective_mobile.todo.controller.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "TODO System Api",
                description = "TODO", version = "1.0.0",
                contact = @Contact(
                        name = "POTEMKIN IVAN",
                        url = "https://github.com/IVANPOTEMKIN"
                )
        )
)
public class Api {
}