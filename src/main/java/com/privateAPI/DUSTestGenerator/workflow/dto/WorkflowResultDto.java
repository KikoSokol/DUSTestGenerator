package com.privateAPI.DUSTestGenerator.workflow.dto;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowResultDto
{
    private PetriNetDto workflow;
    private boolean isCorrect;
    private ReachabilityGraphDto reachabilityGraph;
    private PetriNetDto reachabilityNet;
}
