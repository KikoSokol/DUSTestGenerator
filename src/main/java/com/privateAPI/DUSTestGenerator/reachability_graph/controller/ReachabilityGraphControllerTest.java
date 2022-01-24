package com.privateAPI.DUSTestGenerator.reachability_graph.controller;

import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphGeneratorResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.impl.ReachabilityGraphServiceTest;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("reachability-graph-test")
public class ReachabilityGraphControllerTest {
    @Autowired
    private ReachabilityGraphServiceTest reachabilityGraphServiceTest;



    @GetMapping("sample")
    public ResponseEntity getSampleReachabilityGraph()
    {
        ReachabilityGraphResultDto graphDto = this.reachabilityGraphServiceTest.getSampleReachabilityGraph();
        return new ResponseEntity<>(graphDto, HttpStatus.OK);
    }

    @GetMapping("sample1")
    public ResponseEntity getSampleReachabilityGraphTest1()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test1();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample2")
    public ResponseEntity getSampleReachabilityGraphTest2()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test2();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample3")
    public ResponseEntity getSampleReachabilityGraphTest3()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test3();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample4")
    public ResponseEntity getSampleReachabilityGraphTest4()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test4();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @GetMapping("generator")
    public ResponseEntity getRandomReachabilityGraph()
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphServiceTest.getRandomReachabilityGraph();
        return new ResponseEntity<>(reachabilityGraphDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator-with-parameter")
    public ResponseEntity getRandomReachabilityGraphWithParameter(@RequestBody ReachabilityGraphGeneratorRequest generatorRequest)
    {
        ReachabilityGraphGeneratorResultDto generatorResult = this.reachabilityGraphServiceTest.getRandomReachabilityGraph(generatorRequest);
        return new ResponseEntity<>(generatorResult, HttpStatus.OK);
    }

    @PostMapping("generator-with-parameter-validate")
    public ResponseEntity getRandomReachabilityGraphWithParameterValidate(@RequestBody ReachabilityGraphGeneratorRequest generatorRequest)
    {
        try {
            ReachabilityGraphGeneratorResultDto generatorResult = this.reachabilityGraphServiceTest.getRandomReachabilityGraphValidate(generatorRequest);
            return new ResponseEntity<>(generatorResult, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }


}
