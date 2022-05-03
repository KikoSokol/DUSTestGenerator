package com.privateAPI.DUSTestGenerator.workflow.controller;

import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.workflow.service.WorkflowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("workflow")
public class WorkflowController
{
    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }


    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("random-workflow")
    public ResponseEntity getRandomWorkflow(@RequestBody WorkflowGeneratorRequest workflowGeneratorRequest)
    {
        try
        {
            return new ResponseEntity<>(this.workflowService.getRandomWorkflow(workflowGeneratorRequest), HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("random-correct-workflow")
    public ResponseEntity getRandomCorrectWorkflow(@RequestBody WorkflowGeneratorRequest workflowGeneratorRequest)
    {
        try
        {
            return new ResponseEntity<>(this.workflowService.getRandomCorrectWorkflow(workflowGeneratorRequest), HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
