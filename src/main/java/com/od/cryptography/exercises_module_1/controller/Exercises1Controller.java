package com.od.cryptography.exercises_module_1.controller;

import com.od.cryptography.exercises_module_1.service.Exercises1Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/exercises1")
public class Exercises1Controller {

    private final Exercises1Service service;

    public Exercises1Controller(Exercises1Service service) {
        this.service = service;
    }

}
