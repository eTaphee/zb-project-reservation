package com.zeroboase.reservation.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @PostMapping
    void addStore() {

    }

    @PatchMapping("{id}")
    void updateStore(@PathVariable String id) {

    }

    @DeleteMapping("{id}")
    void deleteStore(@PathVariable String id) {

    }
}
