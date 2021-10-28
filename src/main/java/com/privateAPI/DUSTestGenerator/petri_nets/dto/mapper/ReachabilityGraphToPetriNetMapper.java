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

    }

    private void calculatePlaces () {
        int[] initialMarking =  this.graph.getVertices().get(0).getMarking();
        int placeId = 1;
        for (int marking : initialMarking) {
            PlaceDto place = new PlaceDto(marking, "p" + placeId);
            this.petriNet.addPlace(place);
            placeId++;
        }
    }

    private void calculateTransitions () {
        for(Edge graphEdge : this.graph.getEdges()) {
            this.petriNet.addTransition("t" + graphEdge.getId());
        }
    }

    private void calculateEdges () {
        for(Edge edge : this.graph.getEdges()) {
            System.out.println(Arrays.toString(edge.getMarkingChange()));
        }
    }
}
