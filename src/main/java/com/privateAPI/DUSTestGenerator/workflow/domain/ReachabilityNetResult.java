package com.privateAPI.DUSTestGenerator.workflow.domain;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReachabilityNetResult
{
    private PetriNetDto workflowWithComplementaryPlaces;
    private PetriNetDto reachabilityNet;
    private ReachabilityGraph reachabilityGraphWithComplementaryPlaces;

}
