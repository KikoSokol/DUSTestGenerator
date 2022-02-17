package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;


import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.ReachabilityGraphToPetriNetMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphGeneratorResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import com.privateAPI.DUSTestGenerator.reachability_graph.validator.ReachabilityGraphValidator;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolationException;

@Service
public class ReachabilityGraphServiceImpl implements ReachabilityGraphService
{

    private final ReachabilityGraphGenerator reachabilityGraphGenerator;
    private final ReachabilityGraphMapper reachabilityGraphMapper;
    private final ReachabilityGraphToPetriNetMapper reachabilityGraphToPetriNetMapper;
    private final ReachabilityGraphValidator reachabilityGraphValidator;


    public ReachabilityGraphServiceImpl(ReachabilityGraphGenerator reachabilityGraphGenerator, ReachabilityGraphValidator reachabilityGraphValidator) {
        this.reachabilityGraphGenerator = reachabilityGraphGenerator;
        this.reachabilityGraphMapper = new ReachabilityGraphMapper();
        this.reachabilityGraphToPetriNetMapper = new ReachabilityGraphToPetriNetMapper();
        this.reachabilityGraphValidator = reachabilityGraphValidator;
    }

    @Override
    public ReachabilityGraphGeneratorResultDto getReachabilityGraph(ReachabilityGraphGeneratorRequest reachabilityGraphGeneratorRequest)
    {
        ConstraintViolationException exception = reachabilityGraphValidator.validateReachabilityGraphGeneratorRequest(reachabilityGraphGeneratorRequest);

        if(exception != null)
            throw exception;

        ReachabilityGraphGeneratorResult generatorResult = this.reachabilityGraphGenerator.generateRandomReachabilityGraph(reachabilityGraphGeneratorRequest);
        return this.reachabilityGraphMapper.toReachabilityGraphGeneratorResultDto(generatorResult);
    }

}
