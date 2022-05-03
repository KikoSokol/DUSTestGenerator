package com.privateAPI.DUSTestGenerator.test_generator.controller;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.controller.request.PetriNetGeneratorRequest;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.DefinitionPTIOM0;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PrescriptionPnDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import com.privateAPI.DUSTestGenerator.test_generator.dto.GraphAndTreeTaskDto;
import com.privateAPI.DUSTestGenerator.test_generator.service.MainTestService;
import com.privateAPI.DUSTestGenerator.workflow.controller.request.WorkflowGeneratorRequest;
import com.privateAPI.DUSTestGenerator.workflow.dto.WorkflowResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("test")
public class MainTestController
{
    private final MainTestService mainTestService;


    public MainTestController(MainTestService mainTestService) {
        this.mainTestService = mainTestService;
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("reachability-graph")
    public ResponseEntity getReachabilityGraph(@RequestBody ReachabilityGraphGeneratorRequest request)
    {
        try
        {
            GraphAndTreeTaskDto graphAndTreeTaskDto = this.mainTestService.getReachabilityGraph(request);
            return new ResponseEntity<>(graphAndTreeTaskDto, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("coverability-tree")
    public ResponseEntity getCoverabilityTree(@RequestBody CoverabilityTreeGeneratorRequest request)
    {
        try
        {
            GraphAndTreeTaskDto graphAndTreeTaskDto = this.mainTestService.getCoverabilityTree(request);
            return new ResponseEntity<>(graphAndTreeTaskDto, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("prescription-pn-petri-net")
    public ResponseEntity getPrescriptionPN(@RequestBody PetriNetGeneratorRequest request)
    {
        try
        {
            PrescriptionPnDto prescriptionDto = this.mainTestService.getPrescriptionPN(request);
            return new ResponseEntity<>(prescriptionDto, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("definition-ptiom0")
    public ResponseEntity getDefinitionPTIOM0(@RequestBody PetriNetGeneratorRequest request)
    {
        try
        {
            DefinitionPTIOM0 definitionPTIOM0 = this.mainTestService.getDefinitionPTIOM0(request);
            return new ResponseEntity<>(definitionPTIOM0, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("random-workflow")
    public ResponseEntity getRandomWorkflow(@RequestBody WorkflowGeneratorRequest request)
    {
        try
        {
            WorkflowResultDto workflowResultDto = this.mainTestService.getRandomWorkflow(request);
            return new ResponseEntity<>(workflowResultDto, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("random-correct-workflow")
    public ResponseEntity getRandomCorrectWorkflow(@RequestBody WorkflowGeneratorRequest request)
    {
        try
        {
            WorkflowResultDto workflowResultDto = this.mainTestService.getRandomCorrectWorkflow(request);
            return new ResponseEntity<>(workflowResultDto, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }




}
