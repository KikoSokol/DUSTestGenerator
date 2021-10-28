package com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Edge;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Vertex;

import java.util.Arrays;
import java.util.List;

public class ReachabilityGraphToPetriNetMapper {
    private ReachabilityGraph graph;
    private PetriNetDto petriNet;

    public PetriNetDto calculatePetriNet (ReachabilityGraph graph) {
        this.graph = graph;
        this.petriNet = new PetriNetDto();

        this.startCalculation();

        return this.petriNet;
    }

    private void startCalculation () {
        this.calculatePlaces();
        this.calculateTransitions();
        this.calculateEdges();
    }

    private void calculatePlaces () {
        int[] initialMarking =  this.graph.getVertices().get(0).getMarking();
        int placeId = 1;
        for (int marking : initialMarking) {
            this.petriNet.addPlace(marking, placeId);
            placeId++;
        }
    }

    private void calculateTransitions () {
        for(Edge graphEdge : this.graph.getEdges()) {
            this.petriNet.addTransition(graphEdge.getId());
        }
    }

    private void calculateEdges () {
        for(Edge edge : this.graph.getEdges()) {
            String transitionId = "t" + edge.getId();
            int[] markingChange = edge.getMarkingChange();
            this.calculateEdgesFromChanges(transitionId, markingChange);
        }
    }

    private void calculateEdgesFromChanges (String transitionId, int[] markingChange) {
        for (int index = 0; index < markingChange.length; index++) {
            String placeId = "p" + (index + 1);
            int placeChange = markingChange[index];
            this.calculateOneEdge(placeId, transitionId, placeChange);
        }
    }

    private void calculateOneEdge (String placeId, String transitionId, int placeChange) {
        if (placeChange > 0) {
            this.petriNet.addEdge(transitionId, placeId, placeChange);
        } else if (placeChange < 0) {
            this.petriNet.addEdge(placeId, transitionId, Math.abs(placeChange));
        }
    }
}
