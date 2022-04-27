package com.privateAPI.DUSTestGenerator.test_generator.service;

import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.test_generator.dto.GraphAndTreeTaskDto;

import javax.validation.ConstraintViolationException;

public interface MainTestService
{
    GraphAndTreeTaskDto getReachabilityGraph(ReachabilityGraphGeneratorRequest request) throws ConstraintViolationException;
}
