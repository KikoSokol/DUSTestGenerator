package com.privateAPI.DUSTestGenerator.workflow.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class WorkflowGeneratorRequest
{
    private int minPlaces;
    private int maxPlaces;

    private int minTransition;
    private int maxTransition;
}
