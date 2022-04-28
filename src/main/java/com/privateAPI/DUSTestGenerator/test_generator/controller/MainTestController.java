package com.privateAPI.DUSTestGenerator.test_generator.controller;

import com.privateAPI.DUSTestGenerator.coverability_tree.controller.request.CoverabilityTreeGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import com.privateAPI.DUSTestGenerator.test_generator.dto.GraphAndTreeTaskDto;
import com.privateAPI.DUSTestGenerator.test_generator.service.MainTestService;
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
}
