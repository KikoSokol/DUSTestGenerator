package com.privateAPI.DUSTestGenerator.workflow.service.impl;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import com.privateAPI.DUSTestGenerator.workflow.WorkflowChecker;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.workflow.dto.WorkflowResultDto;
import com.privateAPI.DUSTestGenerator.workflow.generator.StaticPlacesGenerator;
import com.privateAPI.DUSTestGenerator.workflow.generator.WorkflowGenerator;
import com.privateAPI.DUSTestGenerator.workflow.service.WorkflowService;
import com.privateAPI.DUSTestGenerator.workflow.validator.WorkflowValidator;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class WorkflowServiceImpl implements WorkflowService
{
    private final WorkflowGenerator workflowGenerator;
    private final WorkflowChecker workflowChecker;
    private final StaticPlacesGenerator staticPlacesGenerator;
    private final WorkflowValidator workflowValidator;
    private final ReachabilityGraphService reachabilityGraphService;

    public WorkflowServiceImpl(WorkflowGenerator workflowGenerator, WorkflowValidator workflowValidator, ReachabilityGraphService reachabilityGraphService) {
        this.workflowGenerator = workflowGenerator;
        this.workflowValidator = workflowValidator;
        this.reachabilityGraphService = reachabilityGraphService;
        this.workflowChecker = new WorkflowChecker();
        this.staticPlacesGenerator = new StaticPlacesGenerator();
    }

    public WorkflowResultDto getRandomWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest) throws ConstraintViolationException
    {
        workflowGeneratorRequest.setCountStaticPlace(0);

        ConstraintViolationException exception = this.workflowValidator.validate(workflowGeneratorRequest);

        if(exception != null)
            throw exception;

        PetriNetDto workflow = this.workflowGenerator.generateRandomWorkflow(workflowGeneratorRequest);

        boolean isCorrect = this.workflowChecker.isCorrectWorkflow(workflow);

//        if(!isCorrect)
//        {
//            return new WorkflowResultDto(workflow, isCorrect, null, null);
//        }



        return new WorkflowResultDto(workflow, isCorrect, null, null);

    }



}
