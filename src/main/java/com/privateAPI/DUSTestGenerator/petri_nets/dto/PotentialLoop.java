package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PotentialLoop {
    private int placeArrayIndex;
    private Integer weight;
}
