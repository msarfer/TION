package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.Map;

@Controller("/")
public class HomeController {

    @Get("/")
    public Map<String, Object> index() {
        return Map.of("message", "¡Hola, Micronaut!");
    }
}
