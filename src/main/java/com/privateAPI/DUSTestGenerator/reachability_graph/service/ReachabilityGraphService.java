package com.privateAPI.DUSTestGenerator.reachability_graph.service;

import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;

public interface ReachabilityGraphService
{
    ReachabilityGraphDto getReachabilityGraph();

    ReachabilityGraphResultDto getSampleReachabilityGraph();
}
