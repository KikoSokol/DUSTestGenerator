package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public void addTransition (TransitionDto transition) {
        this.transitions.add(transition);
    }

    public void addTransition (int transitionId) {
        TransitionDto transition = new TransitionDto("t" + transitionId);
        this.transitions.add(transition);
    }

    public void addPlace (PlaceDto place) {
        this.places.add(place);
    }

    public void addPlace (int marking, int placeId) {
        PlaceDto place = new PlaceDto(marking, "p" + placeId);
        this.places.add(place);
    }

    public void addEdge (EdgeDto edge) {
        this.edges.add(edge);
    }

    public void addEdge (String from, String to, int weight) {
        EdgeDto edge = new EdgeDto(from, to, weight);
        this.edges.add(edge);
    }
}
