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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        /// Ak nie je korektna workflow - return
        if(!workflowChecker.isCorrectWorkflow(workflow)){
            return;
        }
        this.calculatePlaces();
        this.calculateTransitionsAndEdges();
    }

    private void calculatePlaces(){
        /// Vytvorenie miest podla vrcholov
        for(int i = 0; i < this.reachabilityGraph.getVertices().size(); i++){
            /// 1.vrchol nastavenie tokenov
            if(i == 0){
                PlaceDto placeDto = new PlaceDto(1, "m" + i , false);
                this.reachabilityNet.getPlaces().add(placeDto);
            }else{
                PlaceDto placeDto = new PlaceDto(0, "m" + i , false);
                this.reachabilityNet.getPlaces().add(placeDto);
            }
        }
    }

    private void calculateTransitionsAndEdges () {
        // Vytvorim si mapu s klucom pismen a Stringov
        Map<String, Integer> fromArray = new HashMap<>();
        Map<String, Integer> toArray = new HashMap<>();
        for(int i = 0; i < this.reachabilityGraph.getEdges().size(); i++){
            // Premenna, ktora spravi ak su rovnake transition -> D, D1, D2, ...
            int id = 1;
            for(int j = 0; j < this.reachabilityGraph.getEdges().get(i).getEdgeDirections().size(); j++){
                // Vytiahnem si indexy from a to
                int from  = this.reachabilityGraph.getEdges().get(i).getEdgeDirections().get(j).getFrom().getId();
                int to  = this.reachabilityGraph.getEdges().get(i).getEdgeDirections().get(j).getTo().getId();
                // Ak existuje uz rovnake pismeno a ma nejaku hodnotu tak by mi to do mapy nedalo (tak pridavam cislo napr.D1, ....)
                if(fromArray.containsKey(this.reachabilityGraph.getEdges().get(i).getId())){
                    fromArray.put(this.reachabilityGraph.getEdges().get(i).getId()+id, from);
                    toArray.put(this.reachabilityGraph.getEdges().get(i).getId()+id, to);
                    id = id + 1;
                }else {
                // Inak do mapy dam normalne pismeno a cislo from a to (robim si dve mapy)
                    fromArray.put(this.reachabilityGraph.getEdges().get(i).getId(), from);
                    toArray.put(this.reachabilityGraph.getEdges().get(i).getId(), to);
                }
            }
        }
        System.out.println(fromArray);
        System.out.println(toArray);

        // Vytvorim si listy pre zoradene hodnoty cisiel a pismen ako maju byt vykreslene v poradi
        List<Integer> sortedFromArray =new ArrayList<>();
        List<Integer> sortedToArray =new ArrayList<>();
        List<String> sortedNameArray =new ArrayList<>();

        // Vytahujem minimum z FROM do zoradeneho listu a odstranujem postupne z povodnej mapy + davam zoradene hodnoty pismen do dalsieho listu
        while(!fromArray.isEmpty()) {
            Iterator<Map.Entry<String, Integer>> iter = fromArray.entrySet().iterator();
            Integer minValue = fromArray.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue();
            while (iter.hasNext()) {
                Map.Entry<String, Integer> entry = iter.next();
                if (entry.getValue().equals(minValue)) {
                    sortedFromArray.add(minValue);
                    sortedNameArray.add(entry.getKey());
                    iter.remove();
                }
            }
        }

        // Vytahujem minimum z TO do zoradeneho listu a odstranujem postupne z povodnej mapy
        while(!toArray.isEmpty()) {
            Iterator<Map.Entry<String, Integer>> iter = toArray.entrySet().iterator();
            Integer minValue = toArray.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue();
            while (iter.hasNext()) {
                Map.Entry<String, Integer> entry = iter.next();
                if (entry.getValue().equals(minValue)) {
                    sortedToArray.add(minValue);
                    iter.remove();
                }
            }
        }


        // Tuto by sa malo nahradit v zoradenom liste pismen hodnoty, ktore sa vyskytuju 2x (napr. D, D1 a to D1 by sa malo dat len na D)
        List<String> sortedNameArrayWithoutDuplicates = new ArrayList<>();
        for(int temp = 0; temp < sortedNameArray.size(); temp++){
            String s = sortedNameArray.get(temp).replaceAll("[0-9]", "");
            sortedNameArrayWithoutDuplicates.add(s);
        }


        // Prechadzam zoradeny list ( velkost je rovnaka pri vsetkych )
        for(int j = 0; j < sortedFromArray.size(); j++) {
            // Pridam transition do siete s indexom od 0 --> velkost a davam mu nazov podla zoradeneho listu pismen
            TransitionDto transitionDto = new TransitionDto(""+j, sortedNameArrayWithoutDuplicates.get(j));
            this.reachabilityNet.getTransitions().add(transitionDto);
            // Postupne pridavam hrany z miesta --> prechod a z prechod --> miesto
            this.reachabilityNet.addEdge("m"+sortedFromArray.get(j), ""+transitionDto.getId(), 1);
            this.reachabilityNet.addEdge(""+transitionDto.getId(), "m"+sortedToArray.get(j), 1);
        }

    }
}




