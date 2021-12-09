package com.privateAPI.DUSTestGenerator.coverability_tree.controller;

import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.service.impl.CoverabilityTreeServiceTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coverability-tree-test")
public class CoverabilityTreeControllerTest
{

    private final CoverabilityTreeServiceTest serviceTest;


    public CoverabilityTreeControllerTest(CoverabilityTreeServiceTest serviceTest) {
        this.serviceTest = serviceTest;
    }


    @GetMapping("sample1")
    public ResponseEntity getSample1()
    {
        CoverabilityTreeDto sample1 = this.serviceTest.getSample1();
        return new ResponseEntity<>(sample1, HttpStatus.OK);
    }

    @GetMapping("sample2")
    public ResponseEntity getSample2()
    {
        CoverabilityTreeDto sample2 = this.serviceTest.getSample2();
        return new ResponseEntity<>(sample2, HttpStatus.OK);
    }
}
