package com.privateAPI.DUSTestGenerator.reachability_graph.controller;

import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.impl.ReachabilityGraphServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reachability-graph-test")
public class ReachabilityGraphControllerTest {
    @Autowired
    private ReachabilityGraphServiceTest reachabilityGraphServiceTest;

    @GetMapping("sample")
    public ResponseEntity getSampleReachabilityGraphTest()
    {
        ReachabilityGraph graphDtoTest = this.reachabilityGraphServiceTest.test1();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

}
