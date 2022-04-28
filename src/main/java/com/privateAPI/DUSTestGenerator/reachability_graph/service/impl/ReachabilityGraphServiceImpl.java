package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;


import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.ReachabilityGraphToPetriNetMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphGeneratorResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphMakerResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphState;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
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
    private final ReachabilityGraphMaker reachabilityGraphMaker;


    public ReachabilityGraphServiceImpl(ReachabilityGraphGenerator reachabilityGraphGenerator, ReachabilityGraphValidator reachabilityGraphValidator, ReachabilityGraphMaker reachabilityGraphMaker) {
        this.reachabilityGraphGenerator = reachabilityGraphGenerator;
        this.reachabilityGraphMaker = reachabilityGraphMaker;
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
        PetriNetDto petriNetDto = this.reachabilityGraphToPetriNetMapper.calculatePetriNet(generatorResult.getReachabilityGraph());

        ReachabilityGraphGeneratorResultDto generatorResultDto = this.reachabilityGraphMapper.toReachabilityGraphGeneratorResultDto(generatorResult);

        generatorResultDto.setPetriNetDto(petriNetDto);

        return generatorResultDto;
    }

    @Override
    public ReachabilityGraphGeneratorResultDto fromPetriNetToReachabilityGraph(PetriNetDto petriNetDto)
    {
        ReachabilityGraphMakerResult result = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);


        ReachabilityGraphDto reachabilityGraphDto = null;

        if(result.getState() == ReachabilityGraphState.BOUNDED)
            reachabilityGraphDto = this.reachabilityGraphMapper.toReachabilityGraphDto(result.getReachabilityGraph());

        return new ReachabilityGraphGeneratorResultDto(0, reachabilityGraphDto,
                result.getState());
    }

}
