package com.enigmacamp.restapiintro.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/hello/{name}")
    public String helloPath(@PathVariable("name") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/parse-date")
    public DateResponse parseDate(@RequestParam("date") Long date) {
        var parsed = new DateResponse();
        parsed.date = new Date(date);
        parsed.dateLong = parsed.date.getTime();
        parsed.dateNow = new Date(System.currentTimeMillis()).getTime();

        return parsed;
    }

    public static class DateResponse {
        public Date date;
        public Long dateLong;
        public Long dateNow;
    }
}
