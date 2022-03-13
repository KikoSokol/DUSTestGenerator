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
        if(!isPetriNetWorkflow(petriNetDto))
            return false;

        ReachabilityGraphMakerResult reachabilityGraphResult = this.reachabilityGraphMaker.makeReachabilityGraph(petriNetDto);

        if(reachabilityGraphResult.getState() == ReachabilityGraphState.UNBOUDED)
            return false;

        ReachabilityGraph reachabilityGraph = reachabilityGraphResult.getReachabilityGraph();

        PlaceDto outputPlace = getOutputPlace(petriNetDto);

        return checkIsCorrectWorkflowInReachabilityGraph(petriNetDto, reachabilityGraph, outputPlace);
    }

    public boolean isPetriNetWorkflow(PetriNetDto petriNetDto)
    {
        PlaceDto inputPlace = getInputPlace(petriNetDto);
        if(inputPlace == null)
            return false;

        PlaceDto outputPlace = getOutputPlace(petriNetDto);
        if(outputPlace == null)
            return false;

        if(!isCorrectStartMarking(inputPlace, petriNetDto.getPlaces()))
            return false;

        return true;

    }

    private PlaceDto getInputPlace(PetriNetDto petriNetDto)
    {
        PlaceDto inputPlace = null;

        for(PlaceDto placeDto : petriNetDto.getPlaces())
        {
            if(isPotentiallyInputPlace(placeDto, petriNetDto.getEdges()))
            {
                if(inputPlace == null)
                    inputPlace = placeDto;
                else
                    return null;
            }
        }

        if(inputPlace != null && inputPlace.getNumberOfTokens() != 1)
        {
            return null;
        }
        else
            return inputPlace;
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

    private PlaceDto getOutputPlace(PetriNetDto petriNetDto)
    {
        PlaceDto outputPlace = null;

        for(PlaceDto placeDto : petriNetDto.getPlaces())
        {
            if(isPotentiallyOutputPlace(placeDto, petriNetDto.getEdges())) {
                if (outputPlace == null)
                    outputPlace = placeDto;
                else
                    return null;
            }
        }

        if(outputPlace != null && outputPlace.getNumberOfTokens() > 0)
            return null;

        return outputPlace;
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
            if(placeDto.equals(inputPlace) && placeDto.getNumberOfTokens() != 1)
            {
                return false;
            }
            else if(!placeDto.equals(inputPlace) && placeDto.getNumberOfTokens() > 0)
            {
                return false;
            }

        }
        return true;
    }


    private boolean checkIsCorrectWorkflowInReachabilityGraph(PetriNetDto petriNetDto,
                                                              ReachabilityGraph reachabilityGraph,
                                                              PlaceDto outputPlace)
    {
        Map<PlaceDto, Integer> placesMapToPositionInVertex = getPlacePositionInVertexMarking(petriNetDto);

        Vertex outputVertex = getVertexWithOneTokenInOutputPlace(outputPlace, reachabilityGraph,
                placesMapToPositionInVertex.get(outputPlace));

        if(outputVertex == null)
            return false;

        return isAllPredecessorsAllOtherVertices(outputVertex, reachabilityGraph.getVertices());
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

    private Vertex getVertexWithOneTokenInOutputPlace(PlaceDto outputPlace,
                                                      ReachabilityGraph reachabilityGraph,
                                                      int placePositionInVertexMarking)
    {
        List<Vertex> verticesWithTokensInOutputPlace = new ArrayList<>();

        for(Vertex vertex : reachabilityGraph.getVertices())
        {
            if(vertex.getMarking()[placePositionInVertexMarking] > 0)
                verticesWithTokensInOutputPlace.add(vertex);
        }

        if(verticesWithTokensInOutputPlace.size() != 1)
            return null;

        Vertex oneVertex = verticesWithTokensInOutputPlace.get(0);

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
