package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDto {
    private int numberOfTokens;
    private String id;

    public PlaceDto () {
        this.numberOfTokens = 0;
        this.id = "";
    }
}
