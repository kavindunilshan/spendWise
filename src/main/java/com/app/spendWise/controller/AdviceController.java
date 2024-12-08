package com.app.spendWise.controller;

import com.app.spendWise.entity.Advice;
import com.app.spendWise.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/private/advices", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdviceController {

    @Autowired
    private AdviceService adviceService;

    @PostMapping("")
    public ResponseEntity<Advice> createAdvice(@RequestBody Advice advice) {
        Advice savedAdvice = adviceService.createAdvice(advice);
        return new ResponseEntity<>(savedAdvice, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Advice>> getAdvicesByUserId(@PathVariable String userId) {
        List<Advice> advice = adviceService.getAdvicesByUserId(userId);
        return new ResponseEntity<>(advice, HttpStatus.OK);
    }
}
