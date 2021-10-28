package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;

import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import org.springframework.stereotype.Service;

@Service
public class ReachabilityGraphServiceImpl implements ReachabilityGraphService
{

    private final ReachabilityGraphGenerator reachabilityGraphGenerator;
    private final ReachabilityGraphMapper reachabilityGraphMapper;

    public ReachabilityGraphServiceImpl(ReachabilityGraphGenerator reachabilityGraphGenerator) {
        this.reachabilityGraphGenerator = reachabilityGraphGenerator;
        this.reachabilityGraphMapper = new ReachabilityGraphMapper();
    }

    @Override
    public ReachabilityGraphDto getReachabilityGraph() {
        return null;
    }

    @Override
    public ReachabilityGraphDto getSampleReachabilityGraph() {
        return reachabilityGraphMapper.toReachabilityGraphDto(reachabilityGraphGenerator.hardcodedGenerateReachabilityGraph());
    }


}