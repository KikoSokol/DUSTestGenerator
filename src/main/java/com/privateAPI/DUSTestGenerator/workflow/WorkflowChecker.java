package com.privateAPI.DUSTestGenerator.workflow;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.EdgeDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphMakerResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphState;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowChecker
{
    private final ReachabilityGraphMaker reachabilityGraphMaker;

    public WorkflowChecker() {
        this.reachabilityGraphMaker = new ReachabilityGraphMaker();
    }

    public boolean isCorrectWorkflow(PetriNetDto petriNetDto)
    {
        PlaceDto inputPlace = getInputPlace(petriNetDto);
        if(inputPlace == null)
            return false;
        System.out.println("Vstupné miesto: " + inputPlace.getId());

        if(!isCorrectStartMarking(inputPlace, petriNetDto.getPlaces()))
            return false;

        List<PlaceDto> potentiallyOutputPlaces = getPotentiallyOutputPlaces(petriNetDto);

        if(potentiallyOutputPlaces.size() == 0)
            return false;

        ReachabilityGraphMakerResult reachabilityGraphResult = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);

        if(reachabilityGraphResult.getState() == ReachabilityGraphState.UNBOUDED)
            return false;


        ReachabilityGraph reachabilityGraph = reachabilityGraphResult.getReachabilityGraph();


        return checkIsCorrectWorkflowInReachabilityGraph(petriNetDto, reachabilityGraph, potentiallyOutputPlaces);
    }

    private PlaceDto getInputPlace(PetriNetDto petriNetDto)
    {
        List<PlaceDto> potentiallyInputPlaces = new ArrayList<>();

        for(PlaceDto placeDto : petriNetDto.getPlaces())
        {
            if(isPotentiallyInputPlace(placeDto, petriNetDto.getEdges()))
                potentiallyInputPlaces.add(placeDto);
        }

        for(PlaceDto placeDto : potentiallyInputPlaces)
        {
            if(placeDto.getNumberOfTokens() == 1)
                return placeDto;
        }

        return null;
    }

    private boolean isPotentiallyInputPlace(PlaceDto place, List<EdgeDto> edges)
    {
        int countEdgesOutFromPlace = 0;
        for(EdgeDto edgeDto : edges)
        {
            if(edgeDto.getTo().compareTo(place.getId()) == 0)
                return false;
            else if(edgeDto.getFrom().compareTo(place.getId()) == 0)
                countEdgesOutFromPlace++;
        }

        return countEdgesOutFromPlace != 0;
    }

    private List<PlaceDto> getPotentiallyOutputPlaces(PetriNetDto petriNetDto)
    {
        List<PlaceDto> potentiallyOutputPlaces = new ArrayList<>();

        for(PlaceDto placeDto : petriNetDto.getPlaces())
        {
            if(isPotentiallyOutputPlace(placeDto, petriNetDto.getEdges()))
                potentiallyOutputPlaces.add(placeDto);
        }

        return potentiallyOutputPlaces;
    }

    private boolean isPotentiallyOutputPlace(PlaceDto place, List<EdgeDto> edges)
    {
        for(EdgeDto edgeDto : edges)
        {
            if(edgeDto.getFrom().compareTo(place.getId()) == 0)
                return false;
        }
        return true;
    }

    private boolean isCorrectStartMarking(PlaceDto inputPlace, List<PlaceDto> allPlaces)
    {
        for(PlaceDto placeDto : allPlaces)
        {
            if (!placeDto.equals(inputPlace)) {
                if(placeDto.getNumberOfTokens() > 0)
                    return false;
            }
        }
        return true;
    }


    private boolean checkIsCorrectWorkflowInReachabilityGraph(PetriNetDto petriNetDto,
                                                              ReachabilityGraph reachabilityGraph,
                                                              List<PlaceDto> potentiallyOutputPlaces)
    {
        Map<PlaceDto, Integer> placesMapToPositionInVertex = getPlacePositionInVertexMarking(petriNetDto);

        PlaceDto outputPlace = null;
        Vertex correctVertex = null;

        for(PlaceDto potentiallyOutputPlace : potentiallyOutputPlaces)
        {
            if(outputPlace == null)
            {
                Vertex vertexWithOneTokenInPotentiallyOutputPlace =
                        getVertexWithOneTokenInPotentiallyOutputPlace(potentiallyOutputPlace, reachabilityGraph,
                                placesMapToPositionInVertex.get(potentiallyOutputPlace));
                if(vertexWithOneTokenInPotentiallyOutputPlace != null)
                {
                    outputPlace = potentiallyOutputPlace;
                    correctVertex = vertexWithOneTokenInPotentiallyOutputPlace;
                }
            }
            else
                return false;
        }

        if(outputPlace == null)
            return false;

        System.out.println("Vystupné miesto " + outputPlace.getId());


        return isAllPredecessorsAllOtherVertices(correctVertex, reachabilityGraph.getVertices());
    }

    private Map<PlaceDto, Integer> getPlacePositionInVertexMarking(PetriNetDto petriNetDto)
    {
        Map<PlaceDto, Integer> placesMap = new HashMap<>();

        List<PlaceDto> sortedPlaces = petriNetDto.sortPlaces();

        for(int i = 0; i < sortedPlaces.size(); i++)
        {
            placesMap.put(sortedPlaces.get(i), i);
        }

        return placesMap;
    }

    private Vertex getVertexWithOneTokenInPotentiallyOutputPlace(PlaceDto potentiallyOutputPlace,
                                              ReachabilityGraph reachabilityGraph,
                                              int placePositionInVertexMarking)
    {
        List<Vertex> verticesWithTokensInPotentiallyOutputPlace = new ArrayList<>();

        for(Vertex vertex : reachabilityGraph.getVertices())
        {
            if(vertex.getMarking()[placePositionInVertexMarking] > 0)
                verticesWithTokensInPotentiallyOutputPlace.add(vertex);
        }

        if(verticesWithTokensInPotentiallyOutputPlace.size() != 1)
            return null;

        Vertex oneVertex = verticesWithTokensInPotentiallyOutputPlace.get(0);

        if(oneVertex.getMarking()[placePositionInVertexMarking] != 1)
            return null;

        for(int i = 0; i < oneVertex.getMarking().length; i++)
        {
            if(i != placePositionInVertexMarking)
            {
                if(oneVertex.getMarking()[i] != 0)
                    return null;
            }

        }

        return oneVertex;
    }


    private boolean isAllPredecessorsAllOtherVertices(Vertex vertex, List<Vertex> vertices)
    {
        for(Vertex predecessor : vertices)
        {
            if(predecessor.getId() != vertex.getId())
            {
                if(!vertex.getPredecessors().contains(predecessor.getId()))
                    return false;
            }
        }

        return true;
    }


}
