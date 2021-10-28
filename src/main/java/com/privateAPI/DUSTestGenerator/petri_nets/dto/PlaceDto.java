package com.privateAPI.DUSTestGenerator.petri_nets.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlaceDto {
    private int numberOfTokens;
    private String name;

    public PlaceDto() {
        this.numberOfTokens = 0;
        this.name = "";
    }
}
