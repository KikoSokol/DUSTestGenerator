package com.privateAPI.DUSTestGenerator.workflow.controller;

import com.privateAPI.DUSTestGenerator.workflow.service.WorkflowServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("workflow-test")
public class WorkflowControllerTest
{
    @Autowired
    private WorkflowServiceTest workflowServiceTest;

    @CrossOrigin(origins = "https://lubossremanak.site")
    @GetMapping("random-workflow")
    public ResponseEntity getRandomWorkflow()
    {
        return new ResponseEntity<>(this.workflowServiceTest.getRandomWorkflow(), HttpStatus.OK);
    }
}
