package com.privateAPI.DUSTestGenerator.reachability_graph.controller;

import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
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

    @GetMapping("sample1")
    public ResponseEntity getSampleReachabilityGraphTest1()
    {
        ReachabilityGraphDto graphDtoTest = this.reachabilityGraphServiceTest.test1();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample2")
    public ResponseEntity getSampleReachabilityGraphTest2()
    {
        ReachabilityGraphDto graphDtoTest = this.reachabilityGraphServiceTest.test2();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample3")
    public ResponseEntity getSampleReachabilityGraphTest3()
    {
        ReachabilityGraphDto graphDtoTest = this.reachabilityGraphServiceTest.test3();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample4")
    public ResponseEntity getSampleReachabilityGraphTest4()
    {
        ReachabilityGraphDto graphDtoTest = this.reachabilityGraphServiceTest.test4();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }



}
