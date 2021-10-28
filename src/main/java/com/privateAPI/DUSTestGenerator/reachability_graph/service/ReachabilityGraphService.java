package com.privateAPI.DUSTestGenerator.reachability_graph.service;

import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;

public interface ReachabilityGraphService
{
    ReachabilityGraphDto getReachabilityGraph();

    ReachabilityGraphDto getSampleReachabilityGraph();
}
