package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class PetriNetDto {
    private List<PlaceDto> places;
    private List<TransitionDto> transitions;
    private List<EdgeDto> edges;

    public PetriNetDto () {
        this.places = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addTransition(TransitionDto transition) {
        this.transitions.add(transition);
    }

    public void addTransitionWithNameSameAsId(String transitionId) {
        TransitionDto transition = new TransitionDto("t" + transitionId);
        this.transitions.add(transition);
    }

    public void addTransitionWithNameDifferentAsId(String transitionId, String name) {
        TransitionDto transition = new TransitionDto("t" + transitionId, name);
        this.transitions.add(transition);
    }

    public void addPlace (PlaceDto place) {
        this.places.add(place);
    }

    public void addPlace (int marking, int placeId, boolean isStatic) {
        PlaceDto place = new PlaceDto(marking, "p" + placeId, isStatic);
        this.places.add(place);
    }

    public void addEdge (EdgeDto edge) {
        this.edges.add(edge);
    }

    public void addEdge (String from, String to, int weight) {
        EdgeDto edge = new EdgeDto(from, to, weight);
        this.edges.add(edge);
    }

    public List<PlaceDto> sortPlaces()
    {
        this.places.sort((p1, p2) -> {

            String id1 = p1.getId();
            String id2 = p2.getId();

            if(id1.startsWith("p") && id2.startsWith("p"))
            {
                try {
                    return Integer.parseInt(id1.substring(1)) - Integer.parseInt(id2.substring(1));
                }
                catch (NumberFormatException e)
                {
                    return 0;
                }
            }
            return id1.compareTo(id2);
        });

        return this.places;
    }

    public Set<TransitionDto> getLinkedTransitions() {
        Set<TransitionDto> linked = new HashSet<TransitionDto>();
        for (EdgeDto edge: edges) {
            for (TransitionDto transition : transitions) {
                if (transition.getName().equals(edge.getFrom()) || transition.getName().equals(edge.getTo())) {
                    linked.add(transition);
                }
            }
        }
        return linked;
    }

    public void testPrintPetriNet() {
        System.out.println("\n\n\n--------------------------------------\n");
        System.out.println("Places: " + places.size());
        System.out.println(places);
        System.out.println("\nTransitions: " + transitions.size());
        System.out.println(transitions);
        Set<TransitionDto> linked = this.getLinkedTransitions();
        System.out.println("\nLinked transitions: " +  linked.size());
        System.out.println(linked);
        System.out.println("\nEdges: " + edges.size());
        System.out.println(edges);
    }
}
