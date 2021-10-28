package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PetriNetDto {
    private List<PlaceDto> places;
    private List<String> transitions;
    private List<EdgeDto> edges;

    public PetriNetDto() {
        this.places = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}
