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

    public PetriNetDto getRandomCorrectWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest)
    {
        PetriNetDto petriNetDto = null;
        int count = 0;
        while (true)
        {
            PetriNetDto petriNetDto1 = this.workflowGenerator.generateRandomWorkflow(workflowGeneratorRequest);
            count++;
            System.out.println("COUNT: " + count);
            if(workflowChecker.isCorrectWorkflow(petriNetDto1))
            {
                petriNetDto = petriNetDto1;
                break;
            }
        }

        return petriNetDto;
    }
}