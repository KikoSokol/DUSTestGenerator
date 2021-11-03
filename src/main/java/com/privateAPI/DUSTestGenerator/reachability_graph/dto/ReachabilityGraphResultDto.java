package com.privateAPI.DUSTestGenerator.reachability_graph.dto;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReachabilityGraphResultDto {
    private PetriNetDto petriNet;
    private ReachabilityGraphDto reachabilityGraph;
}
