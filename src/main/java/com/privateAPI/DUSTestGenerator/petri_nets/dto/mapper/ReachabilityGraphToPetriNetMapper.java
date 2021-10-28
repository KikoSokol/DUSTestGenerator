package com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PlaceDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.PotentialLoop;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Edge;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Vertex;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.ArrayList;
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
            this.calculateEdges(edge);
            this.calculateLoopEdges(edge);
        }
    }

    private void calculateEdges (Edge edge) {
        String transitionId = "t" + edge.getId();
        int[] markingChange = edge.getMarkingChange();

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

    private void calculateLoopEdges (Edge edge) {
        String transitionId = "t" + edge.getId();
        int[] markingChange = edge.getMarkingChange();
        List<PotentialLoop> potentialLoops = this.getPotentialLoops(markingChange);
        List<EdgeDirection> directions = edge.getEdgeDirections();

        for (EdgeDirection edgeDirection : directions) {
            if (potentialLoops.isEmpty()) {
                return;
            }
            potentialLoops = this.recalculatePotentialLoops(potentialLoops, edgeDirection);
        }

        this.createLoopEdges(transitionId, potentialLoops);
    }

    private List<PotentialLoop> getPotentialLoops (int[] markingChange) {
        List<PotentialLoop> potentialPlaces = new ArrayList<>();

        for (int index = 0; index < markingChange.length; index++) {
            int placeChange = markingChange[index];
            if (placeChange == 0) {
                PotentialLoop loop = new PotentialLoop(index, null);
                potentialPlaces.add(loop);
            }
        }

        return potentialPlaces;
    }

    private List<PotentialLoop> recalculatePotentialLoops (List<PotentialLoop> potentialLoops, EdgeDirection edgeDirection) {
        int[] beforeMarkings = edgeDirection.getFrom().getMarking();
        int[] afterMarkings = edgeDirection.getTo().getMarking();
        List<PotentialLoop> newPotentialLoops = new ArrayList<>();

        for (PotentialLoop loop : potentialLoops) {
            int placeIndex = loop.getPlaceArrayIndex();
            int beforePlaceMark = beforeMarkings[placeIndex];
            int afterPlaceMark = beforeMarkings[placeIndex];

            if (afterPlaceMark > 0 && beforePlaceMark == afterPlaceMark){
                if (loop.getWeight() == null || loop.getWeight() > afterPlaceMark) {
                    loop.setWeight(afterPlaceMark);
                }
                newPotentialLoops.add(loop);
            }
        }

        return newPotentialLoops;
    }

    private void createLoopEdges (String transitionId, List<PotentialLoop> allLoops) {
        for (PotentialLoop loop : allLoops) {
            String placeId = "p" + (loop.getPlaceArrayIndex() + 1);
            this.petriNet.addEdge(placeId, transitionId, loop.getWeight());
            this.petriNet.addEdge(transitionId, placeId, loop.getWeight());
        }
    }
}
