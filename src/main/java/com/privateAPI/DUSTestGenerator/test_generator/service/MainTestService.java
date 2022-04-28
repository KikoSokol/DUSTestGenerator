package com.privateAPI.DUSTestGenerator.test_generator.service;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.test_generator.dto.GraphAndTreeTaskDto;

import javax.validation.ConstraintViolationException;

public interface MainTestService
{
    GraphAndTreeTaskDto getReachabilityGraph(ReachabilityGraphGeneratorRequest request) throws ConstraintViolationException;
    GraphAndTreeTaskDto getCoverabilityTree(CoverabilityTreeGeneratorRequest request) throws ConstraintViolationException;
}
