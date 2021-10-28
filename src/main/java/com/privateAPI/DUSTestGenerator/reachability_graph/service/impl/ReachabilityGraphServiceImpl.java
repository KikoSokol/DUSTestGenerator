package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.ReachabilityGraphToPetriNetMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import org.springframework.stereotype.Service;

@Service
public class ReachabilityGraphServiceImpl implements ReachabilityGraphService
{

    private final ReachabilityGraphGenerator reachabilityGraphGenerator;
    private final ReachabilityGraphMapper reachabilityGraphMapper;
    private final ReachabilityGraphToPetriNetMapper reachabilityGraphToPetriNetMapper;

    public ReachabilityGraphServiceImpl(ReachabilityGraphGenerator reachabilityGraphGenerator) {
        this.reachabilityGraphGenerator = reachabilityGraphGenerator;
        this.reachabilityGraphMapper = new ReachabilityGraphMapper();
        this.reachabilityGraphToPetriNetMapper = new ReachabilityGraphToPetriNetMapper();
    }

    @Override
    public ReachabilityGraphDto getReachabilityGraph() {
        return null;
    }

    @Override
    public ReachabilityGraphResultDto getSampleReachabilityGraph() {
        ReachabilityGraph graph = this.reachabilityGraphGenerator.hardcodedGenerateReachabilityGraph();
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphMapper.toReachabilityGraphDto(graph);
        PetriNetDto petriNetDto = this.reachabilityGraphToPetriNetMapper.calculatePetriNet(graph);
        return new ReachabilityGraphResultDto(petriNetDto, reachabilityGraphDto);
    }


}
