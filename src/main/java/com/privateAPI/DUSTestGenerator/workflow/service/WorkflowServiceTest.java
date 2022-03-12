package com.privateAPI.DUSTestGenerator.workflow.service;

import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.workflow.generator.WorkflowGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceTest
{

    @Autowired
    private WorkflowGenerator workflowGenerator;


    public PetriNetDto getRandomWorkflow()
    {
        WorkflowGeneratorRequest workflowGeneratorRequest = new WorkflowGeneratorRequest();
        workflowGeneratorRequest.setMinPlaces(1);
        workflowGeneratorRequest.setMaxPlaces(1);
        workflowGeneratorRequest.setMinTransition(1);
        workflowGeneratorRequest.setMaxTransition(1);


        return this.workflowGenerator.generateRandomWorkflow(workflowGeneratorRequest);
    }
}
