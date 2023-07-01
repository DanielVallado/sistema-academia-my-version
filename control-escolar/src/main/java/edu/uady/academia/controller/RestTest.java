package edu.uady.academia.controller;

import jakarta.websocket.server.PathParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
@Log4j2
public class RestTest {

    @GetMapping(value = "/saludo")
    public String saludo() {
        return "Hello World!";
    }

    @GetMapping(value = "/saludo2/{name}")
    public String saludo2(@PathVariable String name, @PathParam("apellidos") String apellidos) {
        log.info("Nombre: " + name);
        log.info("Apellidos: " + apellidos);

        return "Hello, " + name + " " + apellidos;
    }

}
