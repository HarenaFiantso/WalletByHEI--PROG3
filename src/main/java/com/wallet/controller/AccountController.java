package com.wallet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @GetMapping(value= "/api/ping")
    public String method_to_test_spring(){
        String test = "pong";
        return test;
    }
}
