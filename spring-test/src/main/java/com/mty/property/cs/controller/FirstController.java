package com.mty.property.cs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author mty
 * @since 2023/06/04 17:48
 **/
@RestController
@Slf4j
public class FirstController {
    @PostConstruct
    public void init() {
        log.info("init");
    }
    @PostMapping("/t")
    public void test() {
        log.info("test");
    }



}
