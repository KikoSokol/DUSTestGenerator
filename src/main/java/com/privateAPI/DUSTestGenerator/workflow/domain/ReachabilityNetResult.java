package com.privateAPI.DUSTestGenerator.workflow.domain;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReachabilityNetResult
{
    private PetriNetDto workflowWithComplementaryPlaces;
    private PetriNetDto reachabilityNet;
}
