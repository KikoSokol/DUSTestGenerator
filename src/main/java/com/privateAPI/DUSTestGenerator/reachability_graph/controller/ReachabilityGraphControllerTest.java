package com.privateAPI.DUSTestGenerator.reachability_graph.controller;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.ReachabilityGraphToPetriNetMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphGeneratorResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphMakerResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.ReachabilityGraphService;
import com.privateAPI.DUSTestGenerator.reachability_graph.service.impl.ReachabilityGraphServiceTest;
import com.privateAPI.DUSTestGenerator.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("reachability-graph-test")
public class ReachabilityGraphControllerTest {
    @Autowired
    private ReachabilityGraphServiceTest reachabilityGraphServiceTest;

    @Autowired
    private ReachabilityGraphGenerator reachabilityGraphGenerator;

    @Autowired
    private ReachabilityGraphMaker reachabilityGraphMaker;


    @GetMapping("sample")
    public ResponseEntity getSampleReachabilityGraph()
    {
        ReachabilityGraphResultDto graphDto = this.reachabilityGraphServiceTest.getSampleReachabilityGraph();
        return new ResponseEntity<>(graphDto, HttpStatus.OK);
    }

    @GetMapping("sample1")
    public ResponseEntity getSampleReachabilityGraphTest1()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test1();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample2")
    public ResponseEntity getSampleReachabilityGraphTest2()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test2();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample3")
    public ResponseEntity getSampleReachabilityGraphTest3()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test3();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @GetMapping("sample4")
    public ResponseEntity getSampleReachabilityGraphTest4()
    {
        ReachabilityGraphResultDto graphDtoTest = this.reachabilityGraphServiceTest.test4();
        return new ResponseEntity<>(graphDtoTest, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @GetMapping("generator")
    public ResponseEntity getRandomReachabilityGraph()
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphServiceTest.getRandomReachabilityGraph();
        return new ResponseEntity<>(reachabilityGraphDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("generator-with-parameter")
    public ResponseEntity getRandomReachabilityGraphWithParameter(@RequestBody ReachabilityGraphGeneratorRequest generatorRequest)
    {
        ReachabilityGraphGeneratorResultDto generatorResult = this.reachabilityGraphServiceTest.getRandomReachabilityGraph(generatorRequest);
        return new ResponseEntity<>(generatorResult, HttpStatus.OK);
    }

    @PostMapping("generator-with-parameter-validate")
    public ResponseEntity getRandomReachabilityGraphWithParameterValidate(@RequestBody ReachabilityGraphGeneratorRequest generatorRequest)
    {
        try {
            ReachabilityGraphGeneratorResultDto generatorResult = this.reachabilityGraphServiceTest.getRandomReachabilityGraphValidate(generatorRequest);
            return new ResponseEntity<>(generatorResult, HttpStatus.OK);
        }
        catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @PostMapping("generator-with-parameter-validate-mapper-error-example")
    public ResponseEntity reachabilityGraphToPetriNetMapperErrorExample(@RequestBody ReachabilityGraphGeneratorRequest generatorRequest)
    {
        ReachabilityGraphGeneratorResult generatorResult = this.reachabilityGraphGenerator.generateRandomReachabilityGraph(generatorRequest);

        try {
            ReachabilityGraphToPetriNetMapper mapper = new ReachabilityGraphToPetriNetMapper();
            PetriNetDto petriNetDto = mapper.calculatePetriNet(generatorResult.getReachabilityGraph());
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(generatorResult, HttpStatus.OK);

    }

    @GetMapping("triDvaA")
    public ResponseEntity triDvaA()
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphServiceTest.triDvaA();

        return new ResponseEntity<>(reachabilityGraphDto, HttpStatus.OK);
    }

    @GetMapping("triDvaB")
    public ResponseEntity triDvaB()
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphServiceTest.triDvaB();

        return new ResponseEntity<>(reachabilityGraphDto, HttpStatus.OK);
    }

    @GetMapping("triDvaC")
    public ResponseEntity triDvaC()
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphServiceTest.triDvaC();

        return new ResponseEntity<>(reachabilityGraphDto, HttpStatus.OK);
    }

    @GetMapping("fJednaDva")
    public ResponseEntity fJednaDva()
    {
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphServiceTest.fJednaDva();

        return new ResponseEntity<>(reachabilityGraphDto, HttpStatus.OK);
    }



    @PostMapping("fromPetriNetToReachabilityGraph")
    public ResponseEntity fromPetriNetToReachabilityGraph(@RequestBody PetriNetDto petriNetDto)
    {
        ReachabilityGraphMakerResult result = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);

        ReachabilityGraphMapper mapper = new ReachabilityGraphMapper();

        ReachabilityGraphDto dto = mapper.toReachabilityGraphDto(result.getReachabilityGraph());

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
}
