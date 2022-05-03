package com.privateAPI.DUSTestGenerator.workflow.controller;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
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
    @PostMapping("add-static-place")
    public ResponseEntity addStaticPlace(@RequestBody PetriNetDto workflow)
    {
        return new ResponseEntity<>(this.workflowServiceTest.addStaticPlaces(workflow), HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("add-complementary-places")
    public ResponseEntity addComplementaryPlaces(@RequestBody PetriNetDto workflow)
    {
        return new ResponseEntity<>(this.workflowServiceTest.addComplementaryPlaces(workflow), HttpStatus.OK);
    }
}
