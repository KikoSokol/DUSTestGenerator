package com.privateAPI.DUSTestGenerator.reachability_graph.controller;

import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reachability-graph")
public class ReachabilityGraphController
{
    @Autowired
    private ReachabilityGraphService reachabilityGraphService;

    @GetMapping("sample")
    public ResponseEntity getSampleReachabilityGraph()
    {
        ReachabilityGraphResultDto graphDto = this.reachabilityGraphService.getSampleReachabilityGraph();
        return new ResponseEntity<>(graphDto, HttpStatus.OK);
    }


}
