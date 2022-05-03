package com.privateAPI.DUSTestGenerator.workflow.controller;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.workflow.reachability_net.ReachabilityGraphToReachabilityNet;
import com.privateAPI.DUSTestGenerator.workflow.service.WorkflowServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workflow-test")
public class WorkflowControllerTest
{
    @Autowired
    private WorkflowServiceTest workflowServiceTest;

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("random-workflow")
    public ResponseEntity getRandomWorkflow(@RequestBody WorkflowGeneratorRequest workflowGeneratorRequest)
    {
        return new ResponseEntity<>(this.workflowServiceTest.getRandomWorkflow(workflowGeneratorRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("random-correct-workflow")
    public ResponseEntity getRandomCorrectWorkflow(@RequestBody WorkflowGeneratorRequest workflowGeneratorRequest)
    {
        return new ResponseEntity<>(this.workflowServiceTest.getRandomCorrectWorkflow(workflowGeneratorRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("make-reachability-net-from-workflow")
    public ResponseEntity getReachabilityNetFromWorkflow(@RequestBody PetriNetDto petriNetDto)
    {
        return new ResponseEntity<>(this.workflowServiceTest.getReachabilityNetFromWorkflow(petriNetDto), HttpStatus.OK);
    }


}
