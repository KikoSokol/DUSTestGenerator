package com.privateAPI.DUSTestGenerator.workflow.reachability_net;

import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Edge;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.EdgeDirection;
import com.privateAPI.DUSTestGenerator.objects_for_graph_and_tree.domain.Vertex;
import com.privateAPI.DUSTestGenerator.petri_nets.dto.*;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraph;
import com.privateAPI.DUSTestGenerator.reachability_graph.domain.ReachabilityGraphMakerResult;
import com.privateAPI.DUSTestGenerator.reachability_graph.generator.ReachabilityGraphMaker;
import com.privateAPI.DUSTestGenerator.workflow.WorkflowChecker;

import java.util.*;

public class ReachabilityGraphToReachabilityNet {
    private ReachabilityGraphMaker reachabilityGraphMaker;
    private WorkflowChecker workflowChecker;
    private ReachabilityGraph reachabilityGraph;
    private PetriNetDto workflow;
    private PetriNetDto reachabilityNet;

    public PetriNetDto ReachabilityGraphToReachabilityNet(PetriNetDto petriNetDto) {
        this.workflow = petriNetDto;
        this.reachabilityNet = new PetriNetDto();
        this.reachabilityGraphMaker = new ReachabilityGraphMaker();
        this.workflowChecker = new WorkflowChecker();
        this.reachabilityGraph = createReachabilityGraphFromWorkflow();
        this.startCalculation();

        return this.reachabilityNet;
    }

    public ReachabilityGraph createReachabilityGraphFromWorkflow(){
        ReachabilityGraphMakerResult reachabilityGraphMakerResult = this.reachabilityGraphMaker.makeReachabilityGraph(this.workflow);
        return reachabilityGraphMakerResult.getReachabilityGraph();
    }

    private void startCalculation () {
        if(!workflowChecker.isCorrectWorkflow(workflow)){
            return;
        }
        this.calculatePlaces();
        this.calculateTransitions();
        this.calculateEdges();
    }

    private void calculatePlaces(){
        for(int i = 0; i < this.reachabilityGraph.getVertices().size(); i++){
            if(i == 0){
                PlaceDto placeDto = new PlaceDto(1, "m" + i , false);
                this.reachabilityNet.getPlaces().add(placeDto);
            }else{
                PlaceDto placeDto = new PlaceDto(0, "m" + i , false);
                this.reachabilityNet.getPlaces().add(placeDto);
            }
        }
    }

    private void calculateTransitions () {
        int i = 0;
        for(Edge graphEdge : this.reachabilityGraph.getEdges()) {
            if(!graphEdge.getEdgeDirections().isEmpty()){
                TransitionDto transitionDto = new TransitionDto("t"+i);
                this.reachabilityNet.getTransitions().add(transitionDto);
                i++;
            }
        }
    }

    private void calculateEdges () {
        List<Integer> fromArray = new ArrayList<>();
        List<Integer> toArray = new ArrayList<>();
        for(int i = 0; i < this.reachabilityGraph.getEdges().size(); i++){
            for(int j = 0; j < this.reachabilityGraph.getEdges().get(i).getEdgeDirections().size(); j++){
                int from = this.reachabilityGraph.getEdges().get(i).getEdgeDirections().get(j).getFrom().getId();
                int to = this.reachabilityGraph.getEdges().get(i).getEdgeDirections().get(j).getTo().getId();
                fromArray.add(from);
                toArray.add(to);
            }
        }

        fromArray.sort(Comparator.naturalOrder());
        toArray.sort(Comparator.naturalOrder());


        int transition = 0;
        for(int j = 0; j < fromArray.size(); j++){
            if(transition > this.reachabilityNet.getTransitions().size()-1){
                this.reachabilityNet.getTransitions().add(new TransitionDto("t"+transition));
            }
            this.reachabilityNet.addEdge("m"+fromArray.get(j), "t"+transition, 1);
            this.reachabilityNet.addEdge("t"+transition, "m"+toArray.get(j), 1);
            transition++;
        }
    }
}




