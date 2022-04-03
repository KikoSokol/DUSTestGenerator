package com.privateAPI.DUSTestGenerator.workflow.generator;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.TransitionDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphMakerResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphState;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
import com.privateAPI.DUSTestGenerator.reachability_graph.util.ReachabilityGraphParallelPaths;
import com.privateAPI.DUSTestGenerator.reachability_graph.util.ReachabilityGraphWorker;


import java.util.*;
import java.util.stream.Collectors;

public class StaticPlacesGenerator
{

    private final ReachabilityGraphMaker reachabilityGraphMaker;
    private final ReachabilityGraphWorker reachabilityGraphWorker;

    public StaticPlacesGenerator() {
        this.reachabilityGraphMaker = new ReachabilityGraphMaker();
        this.reachabilityGraphWorker = new ReachabilityGraphWorker();
    }

    public PetriNetDto addStaticPlacesToWorkflow(PetriNetDto workflow, int count)
    {
        ReachabilityGraphMakerResult reachabilityGraphMakerResult = this.reachabilityGraphMaker
                .makeReachabilityGraph(workflow);

        if(reachabilityGraphMakerResult.getState() == ReachabilityGraphState.UNBOUDED)
            return null;


        for(int i = 0; i < count; i++)
        {
            workflow = addStaticPlaceToWorkflow(workflow, reachabilityGraphMakerResult.getReachabilityGraph(), i+1);
        }

        return workflow;
    }

    private PetriNetDto addStaticPlaceToWorkflow(PetriNetDto workflow, ReachabilityGraph reachabilityGraph, int id)
    {
        Map<Vertex, Map<Vertex, List<String>>> vertexMap = this.reachabilityGraphWorker
                .getNextVerticesMap(reachabilityGraph);

        List<ReachabilityGraphParallelPaths> parallelPaths = this.reachabilityGraphWorker
                .getAllParallelPaths(reachabilityGraph, vertexMap);

        Vertex fromVertex = getRandomVertexFromStaticPlace(reachabilityGraph.getVertices());
        Map<Vertex, List<String>> nextVertices = vertexMap.get(fromVertex);
        Vertex afterFromVertex = getRandomTransitions(nextVertices);
        List<String> transitionsTakeFromStaticPlace = nextVertices.get(afterFromVertex);
        int positionFromVertex = getPositionVertex(parallelPaths, fromVertex);


        Vertex randomVertexToStaticPlace = getRandomVertexToPlace(vertexMap, afterFromVertex,
                this.reachabilityGraphWorker.getLastIdVertex(reachabilityGraph));
        Map<Vertex, List<String>> nextVerticesToPlace = vertexMap.get(randomVertexToStaticPlace);
        Vertex afterToPlace = getRandomTransitions(nextVerticesToPlace);
        List<String> transitionsToStaticPlace = nextVerticesToPlace.get(afterToPlace);
        int positionRandomVertexToStaticPlace = getPositionVertex(parallelPaths, randomVertexToStaticPlace);


        if(positionFromVertex == -1 && positionRandomVertexToStaticPlace == -1)
        {
            generateAndConnectStaticPlace(workflow, transitionsTakeFromStaticPlace, transitionsToStaticPlace, id);
        }

        return workflow; // vymaz a vrat workflow so statickym miestom
    }

    private Vertex getRandomVertexFromStaticPlace(List<Vertex> vertices)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(vertices.size() - 2);

        return vertices.get(randomIndex);
    }

    private Vertex getRandomVertexToPlace(Map<Vertex, Map<Vertex, List<String>>> vertexMap, Vertex fromVertex,
                                          int idLastVertex)
    {
        Map<Vertex, List<Vertex>> paths = this.reachabilityGraphWorker.getParallelPaths(vertexMap, fromVertex);

        Set<Vertex> possibleVertices = new HashSet<>();


        for(Vertex vertex : paths.keySet())
        {
            for(Vertex listVertex : paths.get(vertex))
            {
                if(listVertex.getId() != idLastVertex)
                    possibleVertices.add(listVertex);
            }
        }

        possibleVertices.add(fromVertex);

        Random random = new Random();
        int randomIndex = random.nextInt(possibleVertices.size());

        return (Vertex) possibleVertices.toArray()[randomIndex];

    }

    private Vertex getRandomTransitions(Map<Vertex, List<String>> vertices)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(vertices.size());

        Vertex nextVertex = null;
        int index = 0;
        for(Vertex vertex : vertices.keySet())
        {
            if(index == randomIndex)
            {
                nextVertex = vertex;
                break;
            }

            index++;
        }
        return nextVertex;
    }


    private int getPositionVertex(List<ReachabilityGraphParallelPaths> parallelPaths, Vertex vertex)
    {
        for(int i = 0; i < parallelPaths.size(); i++)
        {
            Vertex startVertex = parallelPaths.get(i).getStartVertex();
            Vertex endVertex = parallelPaths.get(i).getEndVertex();

            if(startVertex.getId() <= vertex.getId() && vertex.getId() < endVertex.getId())
                return i;
        }

        return -1;
    }


    private PetriNetDto generateAndConnectStaticPlace(PetriNetDto workflow, List<String> fromStaticPlace, List<String> toStaticPlace, int id)
    {
        PlaceDto placeDto = new PlaceDto(1, "s-"  + id, true);

        workflow.getPlaces().add(placeDto);

        for(String from : fromStaticPlace)
        {
            workflow.getEdges().add(new EdgeDto(placeDto.getId(), from, 1));
        }

        for(String to : toStaticPlace)
        {
            workflow.getEdges().add(new EdgeDto(to, placeDto.getId(), 1));
        }

        return workflow;
    }
}
