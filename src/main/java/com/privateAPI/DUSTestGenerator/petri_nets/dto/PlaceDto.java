package com.privateAPI.DUSTestGenerator.petri_nets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDto {
    private int numberOfTokens;
    private String id;
    private boolean isStatic;

    public PlaceDto () {
        this.numberOfTokens = 0;
        this.id = "";
        this.isStatic = false;
    }
}
