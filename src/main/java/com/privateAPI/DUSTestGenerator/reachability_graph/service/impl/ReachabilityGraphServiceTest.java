package com.privateAPI.DUSTestGenerator.reachability_graph.service.impl;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper.ReachabilityGraphToPetriNetMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.controller.request.ReachabilityGraphGeneratorRequest;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.*;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphGeneratorResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.ReachabilityGraphResultDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.dto.mapper.ReachabilityGraphMapper;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphGenerator;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReachabilityGraphServiceTest
{

    private final ReachabilityGraphMaker reachabilityGraphMaker;
    private final ReachabilityGraphMapper reachabilityGraphMapper;
    private final ReachabilityGraphToPetriNetMapper reachabilityGraphToPetriNetMapper;
    private final ReachabilityGraphGenerator reachabilityGraphGenerator;

    public ReachabilityGraphServiceTest(ReachabilityGraphMaker reachabilityGraphMaker, ReachabilityGraphGenerator reachabilityGraphGenerator) {
        this.reachabilityGraphMaker = reachabilityGraphMaker;
        this.reachabilityGraphGenerator = reachabilityGraphGenerator;
        this.reachabilityGraphMapper = new ReachabilityGraphMapper();
        this.reachabilityGraphToPetriNetMapper = new ReachabilityGraphToPetriNetMapper();
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


}
