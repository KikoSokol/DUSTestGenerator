package com.privateAPI.DUSTestGenerator.workflow.service;

import com.privateAPI.DUSTestGenerator.workflow.WorkflowChecker;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.workflow.generator.WorkflowGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceTest
{


    private final WorkflowGenerator workflowGenerator;

    private final WorkflowChecker workflowChecker;

    @Autowired
    public WorkflowServiceTest(WorkflowGenerator workflowGenerator) {
        this.workflowGenerator = workflowGenerator;
        this.workflowChecker = new WorkflowChecker();
    }

    public PetriNetDto getRandomWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest)
    {

        PetriNetDto workflow = this.workflowGenerator.generateRandomWorkflow(workflowGeneratorRequest);

        System.out.println("Je workflow korektna?  " + workflowChecker.isCorrectWorkflow(workflow));

        return workflow;


    }
}
