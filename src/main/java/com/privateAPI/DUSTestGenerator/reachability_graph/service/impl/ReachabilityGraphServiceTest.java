package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.TransitionDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.ReachabilityGraphToPetriNetMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.*;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
import com.privateAPI.DUSTestGenerator.reachability_graph.validator.ReachabilityGraphValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@Service
public class ReachabilityGraphServiceTest
{

    private final ReachabilityGraphMaker reachabilityGraphMaker;
    private final ReachabilityGraphMapper reachabilityGraphMapper;
    private final ReachabilityGraphToPetriNetMapper reachabilityGraphToPetriNetMapper;
    private final ReachabilityGraphGenerator reachabilityGraphGenerator;
    private final ReachabilityGraphValidator reachabilityGraphValidator;

    @Autowired
    private Validator validator;


    public ReachabilityGraphServiceTest(ReachabilityGraphMaker reachabilityGraphMaker,
                                        ReachabilityGraphGenerator reachabilityGraphGenerator,
                                        ReachabilityGraphValidator reachabilityGraphValidator)
    {
        this.reachabilityGraphMaker = reachabilityGraphMaker;
        this.reachabilityGraphGenerator = reachabilityGraphGenerator;
        this.reachabilityGraphMapper = new ReachabilityGraphMapper();
        this.reachabilityGraphToPetriNetMapper = new ReachabilityGraphToPetriNetMapper();
        this.reachabilityGraphValidator = reachabilityGraphValidator;
    }


    public ReachabilityGraphResultDto test1(){
        Vertex vertex1 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges1 = new ArrayList<>();
        edges1.add(new Edge(1, new ArrayList<>(), new int[]{-1,0,1,0,0}));
        edges1.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges1.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));

        return this.getReachabilityGraphResultDto(vertex1, edges1);
    }

    public ReachabilityGraphResultDto test2(){
        Vertex vertex2 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges2 = new ArrayList<>();
        edges2.add(new Edge(1, new ArrayList<>(), new int[]{-1,-1,1,0,0}));
        edges2.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges2.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));

        return this.getReachabilityGraphResultDto(vertex2, edges2);
    }

    public ReachabilityGraphResultDto test3(){
        Vertex vertex3 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges3 = new ArrayList<>();
        edges3.add(new Edge(1, new ArrayList<>(), new int[]{-1,1,1,0,0}));
        edges3.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges3.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));

        return this.getReachabilityGraphResultDto(vertex3, edges3);
    }

    public ReachabilityGraphResultDto test4(){
        Vertex vertex4 = new Vertex(1, new int[]{3,2,0,0,0});
        List<Edge> edges4 = new ArrayList<>();
        edges4.add(new Edge(1, new ArrayList<>(), new int[]{-1,0,1,0,0}));
        edges4.add(new Edge(2, new ArrayList<>(), new int[]{0,0,-1,1,0}));
        edges4.add(new Edge(3, new ArrayList<>(), new int[]{0,0,0,-1,2}));

        return this.getReachabilityGraphResultDto(vertex4, edges4);
    }

    private ReachabilityGraphResultDto getReachabilityGraphResultDto (Vertex vertex, List<Edge> edges) {
        ReachabilityGraphMakerResult reachabilityGraphMakerResult = this.reachabilityGraphMaker.makeReachabilityGraph(vertex, edges);
        ReachabilityGraph reachabilityGraph = reachabilityGraphMakerResult.getReachabilityGraph();

        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphMapper.toReachabilityGraphDto(reachabilityGraph);
        PetriNetDto petriNetDto = this.reachabilityGraphToPetriNetMapper.calculatePetriNet(reachabilityGraph);

        return new ReachabilityGraphResultDto(petriNetDto, reachabilityGraphDto);
    }

    public ReachabilityGraphDto getRandomReachabilityGraph()
    {
        ReachabilityGraph reachabilityGraph = this.reachabilityGraphGenerator.generateRandomReachabilityGraph().getReachabilityGraph();

        return this.reachabilityGraphMapper.toReachabilityGraphDto(reachabilityGraph);
    }

    public ReachabilityGraphGeneratorResult getRandomReachabilityGraphForTest()
    {
        return this.reachabilityGraphGenerator.generateRandomReachabilityGraph();
    }

    public ReachabilityGraphGeneratorResultDto getRandomReachabilityGraph(ReachabilityGraphGeneratorRequest generatorRequest)
    {
        return this.reachabilityGraphMapper.toReachabilityGraphGeneratorResultDto(this.reachabilityGraphGenerator.generateRandomReachabilityGraph(generatorRequest));
    }


    public ReachabilityGraphGeneratorResultDto getRandomReachabilityGraphValidate(ReachabilityGraphGeneratorRequest generatorRequest)
    {

        ConstraintViolationException exception = this.reachabilityGraphValidator.validateReachabilityGraphGeneratorRequest(generatorRequest);

        if(exception != null)
            throw exception;

        return this.reachabilityGraphMapper.toReachabilityGraphGeneratorResultDto(this.reachabilityGraphGenerator.generateRandomReachabilityGraph(generatorRequest));
    }


    public ReachabilityGraphResultDto getSampleReachabilityGraph() {
        ReachabilityGraph graph = this.reachabilityGraphGenerator.hardcodedGenerateReachabilityGraph();
        ReachabilityGraphDto reachabilityGraphDto = this.reachabilityGraphMapper.toReachabilityGraphDto(graph);
        PetriNetDto petriNetDto = this.reachabilityGraphToPetriNetMapper.calculatePetriNet(graph);
        return new ReachabilityGraphResultDto(petriNetDto, reachabilityGraphDto);
    }



    public ReachabilityGraphDto triDvaA()
    {
        List<PlaceDto> placeDtos = new ArrayList<>();
        placeDtos.add(new PlaceDto(1,"a", false));
        placeDtos.add(new PlaceDto(1,"b", false));
        placeDtos.add(new PlaceDto(0,"c", false));
        placeDtos.add(new PlaceDto(0,"d", false));
        placeDtos.add(new PlaceDto(0,"e", false));
        placeDtos.add(new PlaceDto(0,"f", false));
        placeDtos.add(new PlaceDto(0,"g", false));


        List<TransitionDto> transitionDtos = new ArrayList<>();
        transitionDtos.add(new TransitionDto("A"));
        transitionDtos.add(new TransitionDto("B"));
        transitionDtos.add(new TransitionDto("C"));
        transitionDtos.add(new TransitionDto("D"));
        transitionDtos.add(new TransitionDto("E"));

        List<EdgeDto> edgeDtos = new ArrayList<>();
        edgeDtos.add(new EdgeDto("a", "A", 1));
        edgeDtos.add(new EdgeDto("A", "c", 2));
        edgeDtos.add(new EdgeDto("c", "C", 1));
        edgeDtos.add(new EdgeDto("C", "e", 1));
        edgeDtos.add(new EdgeDto("e", "E", 1));
        edgeDtos.add(new EdgeDto("E", "g", 1));
        edgeDtos.add(new EdgeDto("c", "D", 1));
        edgeDtos.add(new EdgeDto("d", "C", 1));
        edgeDtos.add(new EdgeDto("b", "B", 1));
        edgeDtos.add(new EdgeDto("B", "d", 2));
        edgeDtos.add(new EdgeDto("d", "D", 1));
        edgeDtos.add(new EdgeDto("D", "f", 1));

        PetriNetDto petriNetDto = new PetriNetDto(placeDtos, transitionDtos, edgeDtos);

        ReachabilityGraphMakerResult result = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);

        System.out.println(result.getState());


        if(result.getState() == ReachabilityGraphState.UNBOUDED)
            return null;

        return this.reachabilityGraphMapper.toReachabilityGraphDto(result.getReachabilityGraph());

    }

    public ReachabilityGraphDto triDvaB()
    {
        List<PlaceDto> placeDtos = new ArrayList<>();
        placeDtos.add(new PlaceDto(1,"p1", false));
        placeDtos.add(new PlaceDto(1,"p2", false));



        List<TransitionDto> transitionDtos = new ArrayList<>();
        transitionDtos.add(new TransitionDto("a"));
        transitionDtos.add(new TransitionDto("b"));
        transitionDtos.add(new TransitionDto("c"));


        List<EdgeDto> edgeDtos = new ArrayList<>();
        edgeDtos.add(new EdgeDto("p1", "a", 1));
        edgeDtos.add(new EdgeDto("p1", "c", 1));
        edgeDtos.add(new EdgeDto("a", "p2", 1));
        edgeDtos.add(new EdgeDto("p2", "c", 1));
        edgeDtos.add(new EdgeDto("p2", "b", 1));
        edgeDtos.add(new EdgeDto("b", "p2", 1));


        PetriNetDto petriNetDto = new PetriNetDto(placeDtos, transitionDtos, edgeDtos);

        ReachabilityGraphMakerResult result = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);

        System.out.println(result.getState());


        if(result.getState() == ReachabilityGraphState.UNBOUDED)
            return null;

        return this.reachabilityGraphMapper.toReachabilityGraphDto(result.getReachabilityGraph());

    }

    public ReachabilityGraphDto triDvaC()
    {
        List<PlaceDto> placeDtos = new ArrayList<>();
        placeDtos.add(new PlaceDto(1,"p1", false));
        placeDtos.add(new PlaceDto(1,"p2", false));
        placeDtos.add(new PlaceDto(0,"p3", false));
        placeDtos.add(new PlaceDto(0,"p4", false));
        placeDtos.add(new PlaceDto(0,"p5", false));



        List<TransitionDto> transitionDtos = new ArrayList<>();
        transitionDtos.add(new TransitionDto("t1"));
        transitionDtos.add(new TransitionDto("t2"));
        transitionDtos.add(new TransitionDto("t3"));
        transitionDtos.add(new TransitionDto("t4"));


        List<EdgeDto> edgeDtos = new ArrayList<>();
        edgeDtos.add(new EdgeDto("t1", "p1", 1));
        edgeDtos.add(new EdgeDto("t1", "p2", 1));
        edgeDtos.add(new EdgeDto("p1", "t2", 1));
        edgeDtos.add(new EdgeDto("p2", "t3", 1));
        edgeDtos.add(new EdgeDto("t2", "p4", 1));
        edgeDtos.add(new EdgeDto("t2", "p3", 1));
        edgeDtos.add(new EdgeDto("t3", "p4", 1));
        edgeDtos.add(new EdgeDto("p3", "t4", 1));
        edgeDtos.add(new EdgeDto("p4", "t4", 1));
        edgeDtos.add(new EdgeDto("t4", "p5", 1));
        edgeDtos.add(new EdgeDto("p5", "t1", 1));



        PetriNetDto petriNetDto = new PetriNetDto(placeDtos, transitionDtos, edgeDtos);

        ReachabilityGraphMakerResult result = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);

        System.out.println(result.getState());


        if(result.getState() == ReachabilityGraphState.UNBOUDED)
            return null;

        return this.reachabilityGraphMapper.toReachabilityGraphDto(result.getReachabilityGraph());

    }


    public ReachabilityGraphDto fJednaDva()
    {
        List<PlaceDto> placeDtos = new ArrayList<>();
        placeDtos.add(new PlaceDto(3,"p1", false));
        placeDtos.add(new PlaceDto(2,"p2", false));
        placeDtos.add(new PlaceDto(0,"p3", false));
        placeDtos.add(new PlaceDto(0,"p4", false));
        placeDtos.add(new PlaceDto(0,"p5", false));



        List<TransitionDto> transitionDtos = new ArrayList<>();
        transitionDtos.add(new TransitionDto("t1"));
        transitionDtos.add(new TransitionDto("t2"));
        transitionDtos.add(new TransitionDto("t3"));


        List<EdgeDto> edgeDtos = new ArrayList<>();
        edgeDtos.add(new EdgeDto("p1", "t1", 1));
        edgeDtos.add(new EdgeDto("t1", "p2", 1));
        edgeDtos.add(new EdgeDto("p2", "t1", 2));
        edgeDtos.add(new EdgeDto("t1", "p3", 1));
        edgeDtos.add(new EdgeDto("p3", "t2", 1));
        edgeDtos.add(new EdgeDto("t2", "p4", 1));
        edgeDtos.add(new EdgeDto("p4", "t3", 1));
        edgeDtos.add(new EdgeDto("t3", "p5", 2));




        PetriNetDto petriNetDto = new PetriNetDto(placeDtos, transitionDtos, edgeDtos);

        ReachabilityGraphMakerResult result = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);

        System.out.println(result.getState());


        if(result.getState() == ReachabilityGraphState.UNBOUDED)
            return null;

        return this.reachabilityGraphMapper.toReachabilityGraphDto(result.getReachabilityGraph());

    }



}
