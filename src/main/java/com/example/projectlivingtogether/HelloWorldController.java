package com.example.projectlivingtogether;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public List<String> HelloWorld(){
        return Arrays.asList("test", "Hello World");
    }
}
