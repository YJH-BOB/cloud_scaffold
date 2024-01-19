package com.wdt.minio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class FileTest {

    @RequestMapping
    public String test(){
        return "Hello" ;
    }







}