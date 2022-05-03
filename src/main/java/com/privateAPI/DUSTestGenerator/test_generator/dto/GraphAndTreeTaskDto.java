package com.privateAPI.DUSTestGenerator.test_generator.dto;

import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GraphAndTreeTaskDto
{
    private PetriNetDto petriNet;
    private ReachabilityGraphGeneratorResultDto reachabilityGraph;
    private CoverabilityTreeGeneratorResultDto coverabilityTree;

}
