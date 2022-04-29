package com.privateAPI.DUSTestGenerator.workflow.domain;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import lombok.Data;

@Data
public class WorkflowResult
{
    private PetriNetDto workflow;
    private boolean isCorrect;
    private ReachabilityGraph reachabilityGraph;
    private PetriNetDto reachabilityNet;
}
