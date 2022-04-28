package com.privateAPI.DUSTestGenerator.reachability_graph.service;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;

public interface ReachabilityGraphService
{
    ReachabilityGraphGeneratorResultDto getReachabilityGraph(ReachabilityGraphGeneratorRequest reachabilityGraphGeneratorRequest);
    ReachabilityGraphGeneratorResultDto fromPetriNetToReachabilityGraph(PetriNetDto petriNetDto);
}
