package com.privateAPI.DUSTestGenerator.petri_nets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class PetriNetGeneratorRequest{

    @Min(value = 1, message = "Petriho sieť musí mať minimálne 1 miesto")
    private int minPlaces;
    private int maxPlaces;

    @Min(value = 1, message = "Petriho sieť musí mať minimálne 1 prechod")
    private int minTransition;
    private int maxTransition;

    @Min(value = 0, message = "Petriho sieť musí mať 0 alebo viac hrán")
    private int minEdges;
    private int maxEdges;

    @Min(value = 0, message = "Miesto v petriho sieti musí máť 0 alebo viac tokenov")
    private int minToken;
    private int maxToken;

    @Min(value = 1, message = "Hrana v petriho sieti musí mať váhu 1 alebo viac")
    private int minEdgeWeight;
    private int maxEdgeWeight;


}
