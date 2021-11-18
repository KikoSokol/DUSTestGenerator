package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Loop {
    private int placeArrayIndex;

    /**
     * weight on arc going to place
     */
    private Integer inWeight;

    /**
     * weight on arc going to transition
     */
    private Integer outWeight;
}
