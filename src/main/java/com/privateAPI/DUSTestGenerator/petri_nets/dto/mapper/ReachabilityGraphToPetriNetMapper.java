package com.privateAPI.DUSTestGenerator.petri_nets.dto.mapper;

import com.privateAPI.DUSTestGenerator.petri_nets.dto.PetriNetDto;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.Loop;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.Edge;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;

import java.util.ArrayList;
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
            List<Loop> allLoops = this.calculateLoopEdges(edge);
            this.calculateEdges(edge, allLoops);
        }
    }

    private void calculateEdges (Edge edge, List<Loop> excludeLoops) {
        String transitionId = "t" + edge.getId();
        int[] markingChange = edge.getMarkingChange();

        for (int index = 0; index < markingChange.length; index++) {
            if (!isInExcludedLoops(index, excludeLoops)) {
                String placeId = "p" + (index + 1);
                int placeChange = markingChange[index];
                this.calculateOneEdge(placeId, transitionId, placeChange);
            }
        }
    }

    private boolean isInExcludedLoops (int index, List<Loop> excludedLoops) {
        for (Loop loop : excludedLoops) {
            if (index == loop.getPlaceArrayIndex()){
                return true;
            }
        }

        return false;
    }

    private void calculateOneEdge (String placeId, String transitionId, int placeChange) {
        if (placeChange > 0) {
            this.petriNet.addEdge(transitionId, placeId, placeChange);
        } else if (placeChange < 0) {
            this.petriNet.addEdge(placeId, transitionId, Math.abs(placeChange));
        }
    }

    private List<Loop> calculateLoopEdges (Edge edge) {
        String transitionId = "t" + edge.getId();
        int[] markingChange = edge.getMarkingChange();
        List<Loop> loops = this.getPotentialLoops(markingChange);
        List<EdgeDirection> directions = edge.getEdgeDirections();

        for (EdgeDirection edgeDirection : directions) {
            if (loops.isEmpty()) {
                return new ArrayList<Loop>();
            }
            loops = this.recalculatePotentialLoops(loops, edgeDirection);
        }

        this.createLoopEdges(transitionId, loops);

        return loops;
    }

    private List<Loop> getPotentialLoops (int[] markingChange) {
        List<Loop> potentialPlaces = new ArrayList<>();

        for (int index = 0; index < markingChange.length; index++) {
            int placeChange = markingChange[index];
            if (placeChange == 0) {
                Loop loop = new Loop(index, null, null);
                potentialPlaces.add(loop);
            }
        }

        return potentialPlaces;
    }

    private List<Loop> recalculatePotentialLoops (List<Loop> loops, EdgeDirection edgeDirection) {
        int[] beforeMarkings = edgeDirection.getFrom().getMarking();
        int[] afterMarkings = edgeDirection.getTo().getMarking();
        List<Loop> newLoops = new ArrayList<>();

        for (Loop loop : loops) {
            int placeIndex = loop.getPlaceArrayIndex();
            int beforePlaceMark = beforeMarkings[placeIndex];
            int afterPlaceMark = afterMarkings[placeIndex];

            if (afterPlaceMark > 0 && beforePlaceMark == afterPlaceMark){
                if ((loop.getInWeight() == null && loop.getOutWeight() == null) || loop.getInWeight() > afterPlaceMark) {
                    loop.setInWeight(afterPlaceMark);
                    loop.setOutWeight(afterPlaceMark);
                }
                newLoops.add(loop);
            }
        }

        return newLoops;
    }

    private void createLoopEdges (String transitionId, List<Loop> allLoops) {
        for (Loop loop : allLoops) {
            String placeId = "p" + (loop.getPlaceArrayIndex() + 1);
            this.petriNet.addEdge(placeId, transitionId, loop.getOutWeight());
            this.petriNet.addEdge(transitionId, placeId, loop.getInWeight());
        }
    }
}
