package com.privateAPI.DUSTestGenerator.workflow.service;

import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
import com.privateAPI.DUSTestGenerator.workflow.WorkflowChecker;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.workflow.generator.ComplementaryPlaceMaker;
import com.privateAPI.DUSTestGenerator.workflow.generator.StaticPlacesGenerator;
import com.privateAPI.DUSTestGenerator.workflow.generator.WorkflowGenerator;
import com.privateAPI.DUSTestGenerator.workflow.reachability_net.ReachabilityGraphToReachabilityNet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceTest
{


    private final WorkflowGenerator workflowGenerator;

    private final WorkflowChecker workflowChecker;

    private final StaticPlacesGenerator staticPlacesGenerator;

    private final ComplementaryPlaceMaker complementaryPlaceMaker;

    private final ReachabilityGraphToReachabilityNet reachabilityNet;

    @Autowired
    public WorkflowServiceTest(WorkflowGenerator workflowGenerator) {
        this.workflowGenerator = workflowGenerator;
        this.workflowChecker = new WorkflowChecker();
        this.staticPlacesGenerator = new StaticPlacesGenerator();
        this.complementaryPlaceMaker = new ComplementaryPlaceMaker();
        this.reachabilityNet = new ReachabilityGraphToReachabilityNet();
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


    public PetriNetDto addStaticPlaces(PetriNetDto workflow)
    {
        return this.staticPlacesGenerator.addStaticPlacesToWorkflow(workflow, 1);
    }

    public PetriNetDto addComplementaryPlaces(PetriNetDto workflow)
    {
        return this.complementaryPlaceMaker.makeComplementaryPlaces(workflow);
    }

    public PetriNetDto getReachabilityNetFromWorkflow(PetriNetDto petriNetDto)
    {
        PetriNetDto reachabilityNet = this.reachabilityNet.ReachabilityGraphToReachabilityNet(petriNetDto);
        return reachabilityNet;
    }
}
