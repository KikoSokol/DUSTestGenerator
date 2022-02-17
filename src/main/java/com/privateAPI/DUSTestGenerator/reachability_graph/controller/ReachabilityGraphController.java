package com.privateAPI.DUSTestGenerator.reachability_graph.controller;

import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("reachability-graph")
public class ReachabilityGraphController
{
    @Autowired
    private ReachabilityGraphService reachabilityGraphService;

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator")
    public ResponseEntity getRandomReachabilityGraph(@RequestBody ReachabilityGraphGeneratorRequest generatorRequest)
    {
       try
       {
           ReachabilityGraphGeneratorResultDto reachabilityGraph = this.reachabilityGraphService
                   .getReachabilityGraph(generatorRequest);
           return new ResponseEntity<>(reachabilityGraph, HttpStatus.OK);
       }
       catch (ConstraintViolationException e)
       {
           return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
       }
    }


}
