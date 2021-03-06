package com.privateAPI.DUSTestGenerator.test_generator.service.impl;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.coverability_tree.dto.CoverabilityTreeGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.coverability_tree.service.CoverabilityTreeService;
import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.*;
import com.privateAPI.DUSTestGenerator.petri_nets.service.PetriNetService;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import com.privateAPI.DUSTestGenerator.test_generator.dto.GraphAndTreeTaskDto;
import com.privateAPI.DUSTestGenerator.test_generator.service.MainTestService;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.workflow.dto.WorkflowResultDto;
import com.privateAPI.DUSTestGenerator.workflow.service.WorkflowService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MainTestServiceImpl implements MainTestService
{
    private final ReachabilityGraphService reachabilityGraphService;
    private final CoverabilityTreeService coverabilityTreeService;
    private final PetriNetService petriNetService;
    private final WorkflowService workflowService;

    public MainTestServiceImpl(ReachabilityGraphService reachabilityGraphService,
                               CoverabilityTreeService coverabilityTreeService, PetriNetService petriNetService,
                               WorkflowService workflowService) {
        this.reachabilityGraphService = reachabilityGraphService;
        this.coverabilityTreeService = coverabilityTreeService;
        this.petriNetService = petriNetService;
        this.workflowService = workflowService;
    }


    public GraphAndTreeTaskDto getReachabilityGraph(ReachabilityGraphGeneratorRequest request) throws ConstraintViolationException
    {
        ReachabilityGraphGeneratorResultDto reachabilityGraphResult =
                this.reachabilityGraphService.getReachabilityGraph(request);

        CoverabilityTreeGeneratorResultDto coverabilityTreeResult =
                this.coverabilityTreeService.fromPetriNetToCoverabilityTree(reachabilityGraphResult.getPetriNet());

        return new GraphAndTreeTaskDto(reachabilityGraphResult.getPetriNet(), reachabilityGraphResult, coverabilityTreeResult);
    }

    public GraphAndTreeTaskDto getCoverabilityTree(CoverabilityTreeGeneratorRequest request) throws ConstraintViolationException
    {
        CoverabilityTreeGeneratorResultDto coverabilityTreeResult =
                this.coverabilityTreeService.generateCoverabilityTree(request);

        ReachabilityGraphGeneratorResultDto reachabilityTestResult = this.reachabilityGraphService
                .fromPetriNetToReachabilityGraph(coverabilityTreeResult.getPetriNet());

        return new GraphAndTreeTaskDto(coverabilityTreeResult.getPetriNet(), reachabilityTestResult, coverabilityTreeResult);

    }

    public PrescriptionPnDto getPrescriptionPN(PetriNetGeneratorRequest petriNetGeneratorRequest) throws ConstraintViolationException
    {
        return this.petriNetService.getRandomPetriNetWithPrescriptionPN(petriNetGeneratorRequest);
    }

    public DefinitionPTIOM0 getDefinitionPTIOM0(PetriNetGeneratorRequest petriNetGeneratorRequest) throws ConstraintViolationException
    {
        return this.petriNetService.getRandomPetriNetWithDefinitionPNIOM0C(petriNetGeneratorRequest);
    }

    public WorkflowResultDto getRandomWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest) throws ConstraintViolationException
    {
        return this.workflowService.getRandomWorkflow(workflowGeneratorRequest);
    }

    public WorkflowResultDto getRandomCorrectWorkflow(WorkflowGeneratorRequest workflowGeneratorRequest) throws ConstraintViolationException
    {
        return this.workflowService.getRandomCorrectWorkflow(workflowGeneratorRequest);
    }
}
